package ru.Zinchenko.tests.cucTests;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.PendingException;
import io.cucumber.java.ru.*;
//import io.qameta.allure.Epic;
//import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.Zinchenko.DB.JDBC;
import ru.Zinchenko.DB.JDBCImpl;
import ru.Zinchenko.DB.repository.ProductRepository;
import ru.Zinchenko.DB.repository.Repository;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.managers.DriverManager;
import ru.Zinchenko.pages.ProductsPage;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;


public class CucTest {
    private static WebDriver driver;
    private static ProductsPage page;
    private ProductItem newProduct;

    private JDBC jdbc = JDBCImpl.getInstance();
    private Repository rep = new ProductRepository();

    @Допустим("пользователь хочет добавить новый товар")
    public void wantToSetNewProduct() {
    }

    @И("пользователь открыл страницу с товарами")
    public void openPageWithProducts() {
//        driver = new ChromeDriver();
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get(AppProperties.getProperty(PropConst.BASE_URL));

        page = new ProductsPage(driver);
    }

    @After(value = "@only_scenario_hooks")
    public void afterAddNewProduct() {
        newProduct = null;
        page.reset();
        driver.quit();
    }

    @After(value = "@exist_hook")
    public void afterAddExistProduct() {
        page.reset();
        driver.close();
    }

    @AfterAll
    public static void quitDriver(){
        driver.quit();
    }

    @Если("товара нет в таблице")
    public void checkProductExistFalse(DataTable dataTable) {
        ProductItem item = ProductTableTransformer.transform(dataTable);
        if (page.isItemExist(item)) {
            throw new PendingException();
        }
    }

    @То("пользователь нажимает кнопку \"Добавить\"")
    public void getFormForNewProduct() {
    }

    @Когда("пользователь заполняет форму для добавления нового товара")
    public void userSetDataOfNewProduct(DataTable dataTable) {
        newProduct = ProductTableTransformer.transform(dataTable);
    }
    @Когда("нажимает кнопку \"Сохранить\"")
    public void clickButtonSave() {
        page.addNewProduct(newProduct);
    }

    @То("новый товар отображается в списке товаров")
    public void newProductIsOnPage() {
//        Assertions.assertTrue(true);
        Assertions.assertTrue(page.isItemExist(newProduct));
    }


    @Если("товар есть в таблице")
    public void checkProductExistTrue(DataTable dataTable) {
        ProductItem item = ProductTableTransformer.transform(dataTable);
        if (!page.isItemExist(item)) {
            jdbc.connection();
            rep.add(item);
            jdbc.closeConnection();
        }
    }

    @То("не происходит дублирования товаров в списке товаров")
    public void isProductNotDuplicate() {
        jdbc.connection();
        Assertions.assertEquals(1, rep.getCountProductFounded(newProduct));
        jdbc.closeConnection();
//        Assertions.assertTrue(true);
    }
}
