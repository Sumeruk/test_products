package ru.Zinchenko.tests.browserTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import ru.Zinchenko.managers.DriverManager;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeAll
    public static void initAll(){
        driver = DriverManager.getDriver();
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.manage().window().maximize();

        driver.get(AppProperties.getProperty(PropConst.BASE_URL));
    }

    @AfterAll
    public static void closeAll(){
        driver.quit();
    }
}
