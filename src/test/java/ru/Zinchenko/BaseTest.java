package ru.Zinchenko;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeAll
    public static void initEach(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get(AppProperties.getProperty(PropConst.BASE_URL));
    }

    @AfterAll
    public static void close(){
        driver.quit();
    }
}
