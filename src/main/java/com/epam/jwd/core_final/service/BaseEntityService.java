package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.Collection;

public abstract class BaseEntityService<T extends BaseEntity> {

    private static final ApplicationContext APPLICATION_CONTEXT = NassaContext.getInstance();
    private final Class<T> entityClass;

    protected BaseEntityService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Collection<T> findAll() {
        return APPLICATION_CONTEXT.retrieveBaseEntityList(entityClass);
    }

    public boolean checkCoincidenceId(BaseEntity baseEntity, Criteria<T> criteria) {
        return criteria.getId() == null || criteria.getId().equals(baseEntity.getId());
    }

    public boolean checkCoincidenceName(BaseEntity baseEntity, Criteria<T> criteria) {
        return criteria.getName() == null || criteria.getName().equals(baseEntity.getName());
    }
}
