package ru.Zinchenko.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import ru.Zinchenko.DB.JDBC;
import ru.Zinchenko.DB.JDBCImpl;
import ru.Zinchenko.DB.repository.ProductRepository;
import ru.Zinchenko.DB.repository.Repository;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.pages.ProductsPage;
import ru.Zinchenko.properties.ReadJsons;
import ru.Zinchenko.utils.PropConst;

import java.util.List;

public class ProductTest extends BaseTest {
    private static JDBC jdbc = JDBCImpl.getInstance();

    private static ProductsPage productsPage = new ProductsPage(driver);
    private static Repository prodRepository = new ProductRepository();
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

    private static boolean isFruitsAlreadyExists(){
        jdbc.connection();
        int count = 0;
        for (int i = 0; i < fruits.size(); i++) {
            if (prodRepository.getCountProductFounded(fruits.get(i)) != 0){
                count++;
            }

            if (count != 0) return true;
        }
        jdbc.closeConnection();
        return false;
//        productsPage = new ProductsPage(driver);
//        return productsPage.isAddingValid(fruits);
    }
    @Test
    @DisabledIf("isVegetablesAlreadyExists")
    public void testAddVegetables(){

        productsPage.addNewProduct(vegetables.get(0));
        productsPage.addNewProduct(vegetables.get(1));

        Assertions.assertTrue(productsPage.isAddingValid(vegetables));
    }

    private static boolean isVegetablesAlreadyExists(){
        jdbc.connection();
        int count = 0;
        for (int i = 0; i < vegetables.size(); i++) {
            if (prodRepository.getCountProductFounded(vegetables.get(i)) != 0){
                count++;
            }

            if (count != 0) return true;
        }
        jdbc.closeConnection();
        return false;
//        productsPage = new ProductsPage(driver);
//        return productsPage.isAddingValid(vegetables);
    }

    @Test
    public void testAddExistProduct(){
        ProductItem existItem = ReadJsons.readProduct(PropConst.PATH_TO_EXIST_PRODUCT);

        jdbc.connection();
        if (prodRepository.getCountProductFounded(existItem) == 0){
            prodRepository.add(existItem);
        }
        jdbc.closeConnection();

        productsPage.addNewProduct(existItem);
        jdbc.connection();
        Assertions.assertEquals(1, prodRepository.getCountProductFounded(existItem));
        jdbc.closeConnection();

    }

}
