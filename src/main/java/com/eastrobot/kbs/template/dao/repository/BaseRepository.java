package com.eastrobot.kbs.template.dao.repository;

import com.eastrobot.kbs.template.model.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * simple wrapper for jpa
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
    /**
     * whether is logic delete
     *
     * @param entity        entity to delete
     * @param isLogicDelete logic delete flag
     */
    boolean delete(T entity, boolean isLogicDelete);

    /**
     * whether is logic delete
     *
     * @param id            entity to search with key
     * @param isLogicDelete logic delete flag
     */
    boolean deleteById(ID id, boolean isLogicDelete);

}
