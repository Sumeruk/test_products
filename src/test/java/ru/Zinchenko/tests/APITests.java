package ru.Zinchenko.tests;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.Zinchenko.DB.JDBC;
import ru.Zinchenko.DB.JDBCImpl;
import ru.Zinchenko.DB.repository.ProductRepository;
import ru.Zinchenko.DB.repository.Repository;
import ru.Zinchenko.Specifications;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.properties.ReadJsons;
import ru.Zinchenko.utils.PropConst;

import static org.hamcrest.MatcherAssert.assertThat;


import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;


@DisplayName("API тесты для добавления товаров")
public class APITests {
    private static JDBC jdbc = JDBCImpl.getInstance();
    private static Repository prodRepository = new ProductRepository();
    private String JSESSIONID;
    private static List<ProductItem> fruits = ReadJsons.readInProductItemsList(PropConst.PATH_TO_FRUITS_API);
    private static List<ProductItem> vegetables = ReadJsons.readInProductItemsList(PropConst.PATH_TO_VEGETABLES_API);

    @BeforeEach
    public void resetValues() {
        Response resp = given()
                .baseUri(AppProperties.getProperty(PropConst.BASE_URL_API))
                .basePath("/data/reset")
                .post();
        JSESSIONID = resp.getSessionId();
    }

    private static Stream<Arguments> provideFruitsArguments() {
        return fruits.stream().map(Arguments::of);
    }

    private static Stream<Arguments> provideVegetablesArguments() {
        return vegetables.stream().map(Arguments::of);
    }

    @DisplayName("API тест добавления фрукта")
    @ParameterizedTest()
    @MethodSource("provideFruitsArguments")
    public void addFruitTest(ProductItem addItem) {
        Specifications.installSpecifications(
                Specifications.requestSpecifications(AppProperties.getProperty(PropConst.BASE_URL_API)),
                Specifications.responseSpecifications(200));

        given()
                .basePath("/food")
                .cookie("JSESSIONID", JSESSIONID)
                .body(addItem)
                .log().all()
                .when()
                .post()
                .then();

        List<ProductItem> responseListOfProducts =
                given()
                        .basePath("/food")
                        .when()
                        .cookie("JSESSIONID", JSESSIONID)
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .jsonPath()
                        .getList("$.", ProductItem.class);
        assertThat(responseListOfProducts.get(responseListOfProducts.size() - 1), Matchers.equalTo(addItem));


    }

    @DisplayName("API тест добавления овоща")
    @ParameterizedTest
    @MethodSource("provideVegetablesArguments")
    public void addVegetableTest(ProductItem addItem) {
        Specifications.installSpecifications(
                Specifications.requestSpecifications(AppProperties.getProperty(PropConst.BASE_URL_API)),
                Specifications.responseSpecifications(200));

        given()
                .basePath("/food")
                .cookie("JSESSIONID", JSESSIONID)
                .body(addItem)
                .log().all()
                .when()
                .post()
                .then();

        List<ProductItem> responseListOfProducts =
                given()
                        .basePath("/food")
                        .when()
                        .cookie("JSESSIONID", JSESSIONID)
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .jsonPath()
                        .getList("$.", ProductItem.class);

        assertThat(responseListOfProducts.get(responseListOfProducts.size() - 1), Matchers.equalTo(addItem));
    }

    @DisplayName("API тест добавления существующего товара")
//    @Story("API тест добавления существующего товара")
    @Test()
    public void testAddExistProduct(){
        ProductItem existItem = ReadJsons.readProduct(PropConst.PATH_TO_EXIST_PRODUCT);
        ProductItem existItemAPI = ReadJsons.readProduct(PropConst.PATH_TO_EXIST_PRODUCT_API);

        jdbc.connection();
        if (prodRepository.getCountProductFounded(existItem) == 0){
            prodRepository.add(existItem);
        }
        jdbc.closeConnection();

        Specifications.installSpecifications(
                Specifications.requestSpecifications(AppProperties.getProperty(PropConst.BASE_URL_API)),
                Specifications.responseSpecifications(200));

        given()
                .basePath("/food")
                .cookie("JSESSIONID", JSESSIONID)
                .body(existItemAPI)
                .log().all()
                .when()
                .post();


        jdbc.connection();
        Assertions.assertEquals(1, prodRepository.getCountProductFounded(existItem));
        jdbc.closeConnection();

    }

}
