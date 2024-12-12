package ru.Zinchenko.managers;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.Zinchenko.properties.AppProperties;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class DriverManager {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null){
            initDriver();
        }
        return driver;
    }

    private static void initDriver() {
        if (AppProperties.getProperty("type.driver").equals("remote")){
            initRemoteDriver();
        }
        switch (AppProperties.getProperty("type.browser")) {
            case "chrome" -> driver = new ChromeDriver();
            case "firefox" -> driver = new FirefoxDriver();
            default -> Assert.state(false, "Browser doesn't exist");

        }
    }

    private static void initRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("browserName", AppProperties.getProperty("type.browser"));
        selenoidOptions.put("browserVersion", "109.0");
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);
        capabilities.setCapability("selenoid:options", selenoidOptions);
        try {
            driver = new RemoteWebDriver(
                    URI.create(AppProperties.getProperty("selenoid.url")).toURL(),
                    capabilities
            );

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
