package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.SpaceApplicationMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final ApplicationMenu applicationMenu = SpaceApplicationMenu.getInstance();
        final Supplier<ApplicationContext> applicationContextSupplier = applicationMenu::getApplicationContext; // todo
        final NassaContext nassaContext = NassaContext.getInstance();

        nassaContext.init();
        return applicationContextSupplier::get;
    }

    void launchMenu(ApplicationMenu applicationMenu);
}
