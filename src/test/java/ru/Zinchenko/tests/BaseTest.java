package ru.Zinchenko.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeAll
    public static void initAll(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.manage().window().maximize();

        driver.get(AppProperties.getProperty(PropConst.BASE_URL));
    }

    @AfterAll
    public static void closeAll(){
        driver.quit();
    }
}
