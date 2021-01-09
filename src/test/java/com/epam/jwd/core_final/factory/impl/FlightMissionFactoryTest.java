package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.IdDispenser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class FlightMissionFactoryTest {

    private static FlightMissionFactory flightMissionFactory;
    private static final IdDispenser idDispenser = Mockito.mock(IdDispenser.class);

    @BeforeClass
    public static void beforeClass() {
        flightMissionFactory = FlightMissionFactory.getInstance();
    }

    @Test
    public void getInstance_receiveLinkOnObject_whenRequireIt() {
        assertEquals(FlightMissionFactory.class, flightMissionFactory.getClass());
    }

    @Test
    public void create_newFlightMission_whenTryToCreateIt() {
        String name = "Test_Name";
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        Long distance = 1000L;
        Mockito.when(idDispenser.getNextId()).thenReturn(1L);
        FlightMission flightMission = flightMissionFactory.create(name, start, end, distance);
        assertEquals(FlightMission.class, flightMission.getClass());
    }
}