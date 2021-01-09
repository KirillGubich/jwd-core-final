package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.PropertiesLoadingException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() throws PropertiesLoadingException {
        final String propertiesFileName = "src/main/resources/application.properties";

        try (FileInputStream fileInput = new FileInputStream(propertiesFileName)) {
            properties.load(fileInput);
        } catch (FileNotFoundException e) {
            throw new PropertiesLoadingException("Property file cannot be found");
        } catch (IOException e) {
            throw new PropertiesLoadingException("Property file reading error");
        }
    }

    public static ApplicationProperties populateApplicationProperties() {
        return new ApplicationProperties(
                properties.getProperty("inputRootDir"),
                properties.getProperty("outputRootDir"),
                properties.getProperty("crewFileName"),
                properties.getProperty("missionsFileName"),
                properties.getProperty("spaceshipsFileName"),
                Integer.valueOf(properties.getProperty("fileRefreshRate")),
                properties.getProperty("dateTimeFormat")
        );
    }
}
