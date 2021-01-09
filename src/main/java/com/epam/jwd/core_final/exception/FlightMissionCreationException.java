package com.epam.jwd.core_final.exception;

public class FlightMissionCreationException extends RuntimeException {

    private String name;

    public FlightMissionCreationException(String message) {
        super(message);
    }

    public FlightMissionCreationException(String message, String name) {
        super(message);
        this.name = name;
    }

    @Override
    public String toString() {
        return "FlightMission name: " + name + " --> " + getMessage();
    }
}
