package com.eastrobot.kbs.template.dao.repository;

import com.eastrobot.kbs.template.model.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.hql.spi.FilterTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.param.NamedParameterSpecification;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 简单扩展
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-08-08 18:35
 */
@SuppressWarnings("unchecked")
@Slf4j
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public <R> List<R> findListBySQL(@NonNull String sql, Map<String, Object> paramMap) {
        return this.createSqlQuery(sql, paramMap, null).getResultList();
    }

    @Override
    public <R> List<R> findListByHQL(@NonNull String hql, Map<String, Object> paramMap) {
        return this.createHqlQuery(hql, paramMap, null).getResultList();
    }

    @Override
    public <R> Page<R> findPageBySql(@NonNull String sql, Map<String, Object> paramMap, PageRequest page) {
        List list = this.createSqlQuery(sql, paramMap, page).getResultList();
        return PageableExecutionUtils.getPage(list, page, () -> countSqlResult(sql, null, paramMap));
    }

    @Override
    public <R> Page<R> findPageBySql(@NonNull String sql, String countSql, Map<String, Object> paramMap,
                                     PageRequest page) {
        List list = this.createSqlQuery(sql, paramMap, page).getResultList();
        return PageableExecutionUtils.getPage(list, page, () -> countSqlResult(sql, countSql, paramMap));
    }

    @Override
    public <R> Page<R> findPageByHql(@NonNull String hql, Map<String, Object> paramMap, PageRequest page) {
        List list = this.createHqlQuery(hql, paramMap, page).getResultList();
        return PageableExecutionUtils.getPage(list, page, () -> countHqlResult(hql, null, paramMap));
    }

    @Override
    public <R> Page<R> findPageByHql(@NonNull String hql, String countHql, Map<String, Object> paramMap,
                                     PageRequest page) {
        List list = this.createHqlQuery(hql, paramMap, page).getResultList();
        return PageableExecutionUtils.getPage(list, page, () -> countHqlResult(hql, countHql, paramMap));
    }

    @Override
    public Optional<T> findLogicExistById(ID id) {
        Optional<T> rt = super.findById(id);
        if (rt.isPresent()) {
            Class<?> clazz = rt.get().getClass();
            if (!clazz.isAssignableFrom(BaseEntity.class)) {
                return rt;
            } else {
                try {
                    Class<?> superclass = clazz.getSuperclass();
                    Field delFlag = superclass.getDeclaredField("delFlag");
                    delFlag.setAccessible(true);
                    if (delFlag.getInt(superclass) == 0) {
                        return rt;
                    } else {
                        return Optional.empty();
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.info(e.getMessage());
                }
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("deprecation")
    private Query createSqlQuery(@NonNull final String sql, final Map<String, ?> paramMap, PageRequest page) {
        final Query query = (Query) entityManager.createNativeQuery(sql);
        // 这个转换当查询列很多时影响性能.
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Optional.ofNullable(paramMap).ifPresent((v) -> setParameter(query, v));
        Optional.ofNullable(page).ifPresent((p) -> query.setFirstResult((int) p.getOffset()).setMaxResults(p.getPageSize()));
        return query;
    }

    private Query createHqlQuery(@NonNull final String hql, final Map<String, ?> paramMap, PageRequest page) {
        final Query query = (Query) entityManager.createQuery(hql);
        Optional.ofNullable(paramMap).ifPresent((v) -> setParameter(query, v));
        Optional.ofNullable(page).ifPresent((p) -> query.setFirstResult((int) p.getOffset()).setMaxResults(p.getPageSize()));
        return query;
    }

    /**
     * hql分页查询时统计总量
     *
     * @param hql hql
     */
    @SuppressWarnings("deprecation")
    private long countHqlResult(final String hql, String countHql, final Map<String, ?> paramMap) {
        if (StringUtils.isNotBlank(countHql)) {
            return ((BigDecimal) this.createHqlQuery(countHql, paramMap, null).getSingleResult()).longValue();
        } else {
            // 获得翻译器
            SessionFactoryImplementor sfi = entityManager.unwrap(SessionImpl.class).getSessionFactory();
            FilterTranslator translator = sfi.getSessionFactoryOptions()
                    .getServiceRegistry().getService(QueryTranslatorFactory.class)
                    .createFilterTranslator(null, hql, Collections.EMPTY_MAP, sfi);
            translator.compile(Collections.EMPTY_MAP, false);

            //?所对应的参数排序,按原始hql中声明顺序
            List<NamedParameterSpecification> specList =
                    ((QueryTranslatorImpl) translator).getCollectedParameterSpecifications()
                            .stream()
                            // 转成子类NamedParameterSpecification
                            .map(parameterSpecification -> ((NamedParameterSpecification) parameterSpecification))
                            // 按声明顺序排序
                            .sorted(Comparator.comparingInt(o -> o.getSourceColumn() * o.getSourceLine()))
                            .collect(Collectors.toList());

            // 替换?参数为:xx
            // 2018-08-23 15:06 update by Yogurt_lei:FIXME 部分关联关系非必须 考虑优化去除
            String autoCountSql = QueryUtils.createCountQueryFor(translator.getSQLString(), "1");
            String[] split = StringUtils.split(autoCountSql, "?");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < specList.size(); i++) {
                sb.append(split[i]).append(":").append(specList.get(i).getName());
            }
            String countSql = sb.length() == 0 ? autoCountSql : sb.toString();

            log.debug("hql auto count sql is : {}", countSql);

            return this.countSqlResult(null, countSql, paramMap);
        }
    }

    /**
     * sql分页查询时统计总量
     *
     * @param sql sql
     */
    @SuppressWarnings({"deprecation"})
    private long countSqlResult(final String sql, String countSql, final Map<String, ?> paramMap) {
        String autoCountSql = StringUtils.isNotBlank(countSql) ? countSql : QueryUtils.createCountQueryFor(sql, "1");
        log.debug("auto count sql is {}", autoCountSql);

        return Long.parseLong(((HashMap<String, Object>) this.createSqlQuery(autoCountSql, paramMap, null)
                .getSingleResult()).values().stream().findFirst().get().toString());
    }

    private void setParameter(final Query query, final Map<String, ?> paramMap) {
        paramMap.keySet().forEach((v) -> {
            Object obj = paramMap.get(v);
            if (obj instanceof java.util.Date) {
                query.setParameter(v, (Date) obj, TemporalType.TIMESTAMP);
            } else if (obj instanceof Collection<?>) {
                query.setParameterList(v, (Collection<?>) obj);
            } else if (obj instanceof Object[]) {
                query.setParameterList(v, (Object[]) obj);
            } else {
                query.setParameter(v, obj);
            }
        });
    }
}
