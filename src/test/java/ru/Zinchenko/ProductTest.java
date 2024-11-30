package ru.Zinchenko;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.pages.ProductsPage;
import ru.Zinchenko.properties.ReadJsons;
import ru.Zinchenko.utils.PropConst;

import java.util.List;

public class ProductTest extends BaseTest {
    private static ProductsPage productsPage = new ProductsPage(driver);
    private static List<ProductItem> fruits = ReadJsons.readInProductItemsList(PropConst.PATH_TO_FRUITS);

    private static List<ProductItem> vegetables = ReadJsons.readInProductItemsList(PropConst.PATH_TO_VEGETABLES);

    @AfterEach
    public void resetValues(){
        productsPage.reset();
    }
    @Test
    @DisabledIf("isFruitsAlreadyExists")
    public void testAddFruits(){
//        productsPage = new ProductsPage(driver);

        productsPage.addNewProduct(fruits.get(0));
        productsPage.addNewProduct(fruits.get(1));

        Assertions.assertTrue(productsPage.isAddingValid(fruits));
    }

    @Test
    @DisabledIf("isVegetablesAlreadyExists")
    public void testAddVegetables(){

        productsPage.addNewProduct(vegetables.get(0));
        productsPage.addNewProduct(vegetables.get(1));

        Assertions.assertTrue(productsPage.isAddingValid(vegetables));
    }

    private static boolean isFruitsAlreadyExists(){
//        productsPage = new ProductsPage(driver);
        return productsPage.isAddingValid(fruits);
    }

    private static boolean isVegetablesAlreadyExists(){
//        productsPage = new ProductsPage(driver);
        return productsPage.isAddingValid(vegetables);
    }
}
