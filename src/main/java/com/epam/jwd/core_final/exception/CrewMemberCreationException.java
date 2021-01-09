package com.epam.jwd.core_final.exception;

public class CrewMemberCreationException extends RuntimeException {

    private String crewMemberName;

    public CrewMemberCreationException(String message) {
        super(message);
    }

    public CrewMemberCreationException(String message, String crewMemberName) {
        super(message);
        this.crewMemberName = crewMemberName;
    }

    @Override
    public String toString() {
        return "CrewMember name: " + crewMemberName + " --> " + getMessage();
    }
}
