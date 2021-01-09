package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.SpaceApplicationMenu;
import com.epam.jwd.core_final.domain.ApplicationStatus;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        System.out.println("Choose action: ");
        System.out.println("1 - get crew members info");
        System.out.println("2 - update crew member info");
        System.out.println("3 - get spaceships info");
        System.out.println("4 - update spaceship info");
        System.out.println("5 - create mission");
        System.out.println("6 - update mission information");
        System.out.println("7 - simulate mission");
        System.out.println("8 - get output file with missions");
        System.out.println("0 - close application");
    }

    default ApplicationStatus handleUserInput(String userInput) {
        return SpaceApplicationMenu.getInstance().handleUserInput(userInput);
    }
}
