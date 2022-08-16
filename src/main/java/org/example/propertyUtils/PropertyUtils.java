package org.example.propertyUtils;

import org.example.constants.FrameworkConstants;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyUtils {

    private static final Properties property = new Properties();

    private PropertyUtils() {}

    static {
        try(FileInputStream file = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
            property.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = property.getProperty(key.toLowerCase());
        if (Objects.isNull(value)) throw new NullPointerException("Property name " + key + " is not found.");
        return value;
    }
}
