package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.SpaceApplication;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ApplicationMenu applicationMenu = Application.start();
            LOGGER.info("Application started");
            Application application = SpaceApplication.getInstance();
            application.launchMenu(applicationMenu);
            LOGGER.info("Application finished successfully");
        } catch (InvalidStateException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("Application launch failed");
        }
    }
}