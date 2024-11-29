package ru.Zinchenko;

import org.junit.jupiter.api.*;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.pages.ProductsPage;
import ru.Zinchenko.utils.TypeOfProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductTest extends BaseTest {
    private static ProductsPage productsPage;

    @AfterEach
    public void resetValues(){
        productsPage.reset();
    }
    @Test
    public void testAddFruits(){
        productsPage = new ProductsPage(driver);

        ProductItem itemBanana = new ProductItem("банан", TypeOfProduct.FRUIT.getName(), false);
        ProductItem itemDurian = new ProductItem("Дуриан", TypeOfProduct.FRUIT.getName(), true);

        productsPage.addNewProduct(itemBanana);
        productsPage.addNewProduct(itemDurian);

        List<ProductItem> items = new ArrayList<>();
        items.add(itemBanana);
        items.add(itemDurian);

        Assertions.assertTrue(productsPage.isAddingValid(items));
    }

    @Test
    public void testAddVegetables(){
        productsPage = new ProductsPage(driver);

        ProductItem item1 = new ProductItem("картофель", TypeOfProduct.VEGETABLE.getName(), false);
        ProductItem item2 = new ProductItem("Мандурия", TypeOfProduct.VEGETABLE.getName(), true);

        productsPage.addNewProduct(item1);
        productsPage.addNewProduct(item2);

        List<ProductItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Assertions.assertTrue(productsPage.isAddingValid(items));
    }
}
