package ru.Zinchenko.tests.cucTests;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.PendingException;
import io.cucumber.java.ru.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.pages.ProductsPage;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;

public class CucTest {
    private static WebDriver driver;
    private static ProductsPage page;
    private ProductItem newProduct;

    @Допустим("пользователь хочет добавить новый товар")
    public void пользователь_хочет_добавить_новый_товар() {
    }

    @И("пользователь открыл страницу с товарами")
    public void пользователь_открыл_страницу_с_товарами() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get(AppProperties.getProperty(PropConst.BASE_URL));

        page = new ProductsPage(driver);
    }

    @After(value = "@only_scenario_hooks")
    public void after() {
        System.out.println("after scenario");
        page.reset();
        driver.quit();
    }

    @Если("товара нет в таблице")
    public void checkProductExist(DataTable dataTable) {
        ProductItem item = ProductTableTransformer.transform(dataTable);
        if (page.isItemExist(item)) {
            throw new PendingException();
        }
    }

    @То("пользователь нажимает кнопку \"Добавить\"")
    public void getFormForNewProduct() {
    }

    @Когда("пользователь заполняет форму для добавления нового товара")
    public void пользователь_заполняет_форму_для_добавления_нового_товара(DataTable dataTable) {
        newProduct = ProductTableTransformer.transform(dataTable);
    }
    @Когда("нажимает кнопку \"Сохранить\"")
    public void нажимает_кнопку() {
        page.addNewProduct(newProduct);
    }
    @То("новый товар отображается в списке товаров")
    public void новый_товар_отображается_в_списке_товаров() {
        Assertions.assertTrue(page.isItemExist(newProduct));
    }
}
