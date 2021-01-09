package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.IdDispenser;

import java.time.LocalDate;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    private final IdDispenser idDispenser = EntityIdDispenser.getInstance();
    private static FlightMissionFactory instance;

    private FlightMissionFactory() {
    }

    public static FlightMissionFactory getInstance() {
        if (instance == null) {
            instance = new FlightMissionFactory();
        }
        return instance;
    }

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission(idDispenser.getNextId(),
                (String) args[0],
                (LocalDate) args[1],
                (LocalDate) args[2],
                (Long) args[3]);
    }
}
