package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.IdDispenser;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    private final IdDispenser idDispenser = EntityIdDispenser.getInstance();
    private static SpaceshipFactory instance;

    private SpaceshipFactory() {
    }

    public static SpaceshipFactory getInstance() {
        if (instance == null) {
            instance = new SpaceshipFactory();
        }
        return instance;
    }

    @Override
    public Spaceship create(Object... args) {
        return new Spaceship(idDispenser.getNextId(),
                (String) args[0],
                (Map<Role, Short>) args[1],
                (Long) args[2]);
    }
}
