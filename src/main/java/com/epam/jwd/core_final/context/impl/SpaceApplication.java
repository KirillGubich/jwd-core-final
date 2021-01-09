package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.ApplicationStatus;
import com.epam.jwd.core_final.util.UserInput;

public class SpaceApplication implements Application {

    private static SpaceApplication instance;

    private SpaceApplication() {
    }

    public static SpaceApplication getInstance() {
        if (instance == null) {
            instance = new SpaceApplication();
        }
        return instance;
    }

    @Override
    public void launchMenu(ApplicationMenu applicationMenu) {
        ApplicationStatus applicationStatus = ApplicationStatus.IN_PROGRESS;
        while (applicationStatus != ApplicationStatus.COMPLETED) {
            applicationMenu.printAvailableOptions();
            applicationStatus = applicationMenu.handleUserInput(UserInput.getStringFromConsole());
        }
    }
}
