package org.example.constants;

public class FrameworkConstants {

    private FrameworkConstants() {}

    private static final String RESOURCES_PATH = System.getProperty("user.dir");

    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/src/test/resources/config.properties";

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    public static String getResourcesPath() {
        return RESOURCES_PATH;
    }
}
