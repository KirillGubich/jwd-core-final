package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.SpaceshipAssignmentException;
import com.epam.jwd.core_final.exception.SpaceshipCreationException;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ConcreteSpaceshipServiceTest {

    private static SpaceshipService spaceshipService;

    @BeforeClass
    public static void beforeClass() {
        spaceshipService = ConcreteSpaceshipService.getInstance();
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(ConcreteSpaceshipService.class, spaceshipService.getClass());
    }

    @Test
    public void findAllSpaceshipsByCriteria_emptyList_whenParamIsNull() {
        List<Spaceship> spaceships = spaceshipService.findAllSpaceshipsByCriteria(null);
        assertTrue(spaceships.isEmpty());
    }

    @Test
    public void findSpaceshipByCriteria_emptyOptional_whenParamIsNull() {
        Optional<Spaceship> spaceship = spaceshipService.findSpaceshipByCriteria(null);
        assertFalse(spaceship.isPresent());
    }

    @Test(expected = SpaceshipUpdateException.class)
    public void updateSpaceshipDetails_throwException_whenParamIsNull() {
        spaceshipService.updateSpaceshipDetails(null);
    }

    @Test(expected = SpaceshipAssignmentException.class)
    public void assignSpaceshipOnMission_throwException_whenParamIsNull() {
        spaceshipService.assignSpaceshipOnMission(null);
    }

    @Test(expected = SpaceshipAssignmentException.class)
    public void assignSpaceshipOnMission_throwException_whenIsNoReadyForNextMissions() {
        Spaceship spaceship = SpaceshipFactory.getInstance().create(null, null, null);
        spaceship.setReadyForNextMissions(false);
        spaceshipService.assignSpaceshipOnMission(spaceship);
    }

    @Test(expected = SpaceshipCreationException.class)
    public void createSpaceship_throwException_whenParamIsNull() {
        spaceshipService.createSpaceship(null);
    }
}