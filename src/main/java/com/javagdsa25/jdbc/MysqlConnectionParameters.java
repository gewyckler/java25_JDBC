package com.javagdsa25.jdbc;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class MysqlConnectionParameters {
    private static final String PROPERTIES_FILE_NAME = "/jdbc.properties";
    private Properties properties;

    private String dbHOST;
    private String dbPORT;
    private String dbUSERNAME;
    private String dbPASSWORD;
    private String dbNAME;

    public MysqlConnectionParameters() throws IOException {
        loadConfigurationFrom(PROPERTIES_FILE_NAME);

        dbHOST = loadParameter("jdbc.database.host");
        dbPORT = loadParameter("jdbc.database.port");
        dbUSERNAME = loadParameter("jdbc.username");
        dbPASSWORD = loadParameter("jdbc.password");
        dbNAME = loadParameter("jdbc.database.name");
    }

    public Properties loadConfigurationFrom(String resourceName) throws IOException {
        properties = new Properties();

        InputStream propertiesStream = this.getClass().getResourceAsStream(resourceName);
        if (propertiesStream == null) {
            System.err.println("Resource cannot be loaded.");
            throw new FileNotFoundException();
        }
        properties.load(propertiesStream);
        return properties;
    }

    private String loadParameter(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
