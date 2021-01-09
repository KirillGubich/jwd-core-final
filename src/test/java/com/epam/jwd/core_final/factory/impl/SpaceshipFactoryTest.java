package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.IdDispenser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SpaceshipFactoryTest {

    private static SpaceshipFactory spaceshipFactory;
    private static final IdDispenser idDispenser = Mockito.mock(IdDispenser.class);

    @BeforeClass
    public static void beforeClass() {
        spaceshipFactory = SpaceshipFactory.getInstance();
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(SpaceshipFactory.class, spaceshipFactory.getClass());
    }

    @Test
    public void create_newSpaceship_whenTryToCreateIt() {
        String name = "Test_Name";
        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.MISSION_SPECIALIST, new Short("4"));
        crew.put(Role.FLIGHT_ENGINEER, new Short("3"));
        crew.put(Role.PILOT, new Short("2"));
        crew.put(Role.COMMANDER, new Short("1"));
        Long distance = 1000L;
        Mockito.when(idDispenser.getNextId()).thenReturn(1L);
        Spaceship spaceship = spaceshipFactory.create(name, crew, distance);
        assertEquals(Spaceship.class, spaceship.getClass());
    }
}