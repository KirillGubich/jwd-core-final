package com.epam.jwd.core_final.exception;

public class SpaceshipCreationException extends RuntimeException {

    private String name;

    public SpaceshipCreationException(String message) {
        super(message);
    }

    public SpaceshipCreationException(String message, String name) {
        super(message);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spaceship " + name + " " + getMessage();
    }
}
