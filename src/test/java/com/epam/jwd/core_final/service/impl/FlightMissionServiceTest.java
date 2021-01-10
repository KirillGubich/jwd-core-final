package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.FlightMissionCreationException;
import com.epam.jwd.core_final.exception.FlightMissionUpdateException;
import com.epam.jwd.core_final.service.MissionService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlightMissionServiceTest {

    private static MissionService missionService;

    @BeforeClass
    public static void beforeClass() {
        missionService = FlightMissionService.getInstance();
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(FlightMissionService.class, missionService.getClass());
    }

    @Test
    public void findAllMissionsByCriteria_emptyList_whenParamIsNull() {
        List<FlightMission> flightMissions = missionService.findAllMissionsByCriteria(null);
        assertTrue(flightMissions.isEmpty());
    }

    @Test
    public void findMissionByCriteria_emptyOptional_whenParamIsNull() {
        Optional<FlightMission> mission = missionService.findMissionByCriteria(null);
        assertFalse(mission.isPresent());
    }

    @Test(expected = FlightMissionUpdateException.class)
    public void updateMissionDetails_throwException_whenParamIsNull() {
        missionService.updateMissionDetails(null);
    }

    @Test(expected = FlightMissionCreationException.class)
    public void createMission_throwException_whenParamIsNull() {
        missionService.createMission(null);
    }
}