package ru.Zinchenko.properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.Zinchenko.items.ProductItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadJsons {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<ProductItem> readInProductItemsList(String nameFile){
        File json = new File(nameFile);
        try {
            return objectMapper.readValue(json, new TypeReference<>(){});
        } catch (IOException io){
            System.out.println(io.getMessage());
            return null;
        }
    }

    public static ProductItem readProduct(String nameFile){
        File json = new File(nameFile);
        try {
            return objectMapper.readValue(json, ProductItem.class);
        } catch (IOException io){
            System.out.println(io.getMessage());
            return null;
        }
    }
}
