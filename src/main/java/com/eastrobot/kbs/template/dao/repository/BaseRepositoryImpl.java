package com.eastrobot.kbs.template.dao.repository;

import com.eastrobot.kbs.template.model.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Slf4j
@NoRepositoryBean
public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private EntityManager em;
    private JpaEntityInformation<T, ID> entityInformation;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.em = em;
    }

    @Override
    public void delete(T entity, boolean isLogicDelete) {
        if (!isLogicDelete) {
            super.delete(entity);
        } else {
            entity.setDelFlag(1);
            super.save(entity);
        }
    }

    @Override
    public void deleteById(ID id, boolean isLogicDelete) {
        if (!isLogicDelete) {
            super.deleteById(id);
        } else {
            super.findById(id).ifPresent(v -> {
                v.setDelFlag(1);
                super.save(v);
            });
        }
    }
}
