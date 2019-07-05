package com.eastrobot.kbs.template.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 简单扩展
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-08-08 18:10
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

    /**
     * sql 查询结果list
     *
     * @param sql      sql
     * @param paramMap 参数map 可为null
     */
    <R> List<R> findListBySQL(@NonNull String sql, @Nullable Map<String, Object> paramMap);

    /**
     * hql 查询结果list
     *
     * @param hql      hql
     * @param paramMap 参数map 可为null,hql中对应参数为:param
     */
    <R> List<R> findListByHQL(@NonNull String hql, @Nullable Map<String, Object> paramMap);

    /**
     * 根据sql分页查询,自动解析countSql
     *
     * @param sql      sql
     * @param paramMap 参数map 可为null,sql中对应参数为:param
     * @param page     分页参数 {@link com.eastrobot.kbs.template.util.PageUtil}
     */
    <R> Page<R> findPageBySql(@NonNull String sql, @Nullable Map<String, Object> paramMap, PageRequest page);

    /**
     * 根据sql分页查询,未提供countSql将自动计算
     *
     * @param sql      SQL query to execute
     * @param countSql 分页统计总量的countSql  为null将自动解析countSql
     * @param paramMap 参数map 可为null,sql中对应参数为:param
     * @param page     分页参数 {@link com.eastrobot.kbs.template.util.PageUtil}
     */
    <R> Page<R> findPageBySql(@NonNull String sql, @Nullable String countSql, @Nullable Map<String, Object> paramMap,
                              PageRequest page);

    /**
     * 根据hql分页查询,自动解析countHql
     *
     * @param hql      hql
     * @param paramMap 参数map 可为null,hql中对应参数为:param
     * @param page     分页参数 {@link com.eastrobot.kbs.template.util.PageUtil}
     */
    <R> Page<R> findPageByHql(@NonNull String hql, Map<String, Object> paramMap, PageRequest page);

    /**
     * 根据hql分页查询,未提供countHql将自动计算
     *
     * @param hql      hql
     * @param countHql 分页统计总量的countHql 为null将自动解析countHql
     * @param paramMap 参数map 可为null,hql中对应参数为:param
     * @param page     分页参数 {@link com.eastrobot.kbs.template.util.PageUtil}
     */
    <R> Page<R> findPageByHql(@NonNull String hql, @Nullable String countHql, @Nullable Map<String, Object> paramMap,
                              PageRequest page);

    /**
     * 查找逻辑存在的唯一对象 entity extend BasisBean and delFlag = 0
     *
     * @param id 对象id
     */
    Optional<T> findLogicExistById(ID id);
}
