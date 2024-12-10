package ru.Zinchenko;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.devtools.v85.network.model.Request;

public class Specifications {

    public static RequestSpecification requestSpecifications(String baseURL){
        return new RequestSpecBuilder()
                .setBaseUri(baseURL)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public static ResponseSpecification responseSpecifications(int statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static void installSpecifications(RequestSpecification reqSpec, ResponseSpecification respSpec){
        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = respSpec;
    }
}
