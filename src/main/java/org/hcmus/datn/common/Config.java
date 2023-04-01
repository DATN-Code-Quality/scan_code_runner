package org.hcmus.datn.common;

import java.util.Properties;

public class Config {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
