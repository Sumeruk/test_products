package ru.Zinchenko.properties;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private static FileInputStream fileInputStream;
    private static Properties applicationProp;
    private static Properties systemProp;

    static {
        try {
            fileInputStream = new FileInputStream("src/test/resources/application.properties");
            applicationProp = new Properties();
            applicationProp.load(fileInputStream);

            systemProp = System.getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getProperty(String key) {
        if (!(systemProp.getProperty(key) == null)){
            return systemProp.getProperty(key);
        }
        return applicationProp.getProperty(key);
    }
}

