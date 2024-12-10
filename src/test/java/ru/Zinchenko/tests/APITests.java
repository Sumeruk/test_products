package ru.Zinchenko.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Zinchenko.Specifications;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.List;

import static io.restassured.RestAssured.*;


public class APITests {
    ObjectMapper objectMapper = new ObjectMapper();
    private String JSESSIONID = "260C04CBDED283942832EFBD2AFCF55D";

    @BeforeEach
    public void resetValues(){
        given()
                .baseUri(AppProperties.getProperty(PropConst.BASE_URL_API))
                .cookie("JSESSIONID", JSESSIONID)
                .basePath("/data/reset")
                .when()
                .post();
    }

    @Test
    public void addProductTest() {
        ProductItem addItem = new ProductItem("banan", "FRUIT", false);

        Specifications.installSpecifications(
                Specifications.requestSpecifications(AppProperties.getProperty(PropConst.BASE_URL_API)),
                Specifications.responseSpecifications(200));

        given()
                .basePath("/food")
                .cookie("JSESSIONID", JSESSIONID)
                .body(addItem)
                .when()
                .post()
                .then();


        List<ProductItem> responseList =
                given()
                .baseUri(AppProperties.getProperty(PropConst.BASE_URL_API))
                .basePath("/food")
                .when()
                .cookie("JSESSIONID", JSESSIONID)
                .get()
                .then()
                .extract()
                .jsonPath()
                .getList("$.", ProductItem.class);

        assertThat(responseList.get(responseList.size() - 1), Matchers.equalTo(addItem));

    }

}
