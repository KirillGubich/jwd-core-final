package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.factory.IdDispenser;

public final class EntityIdDispenser implements IdDispenser {

    private Long nextId = 1L;
    private static EntityIdDispenser instance;

    private EntityIdDispenser() {
    }

    public static EntityIdDispenser getInstance() {
        if (instance == null) {
            instance = new EntityIdDispenser();
        }
        return instance;
    }

    @Override
    public Long getNextId() {
        return nextId++;
    }
}
