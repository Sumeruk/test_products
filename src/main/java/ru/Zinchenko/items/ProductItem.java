package ru.Zinchenko.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"name", "type", "exotic"})
public class ProductItem {
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    @JsonProperty("exotic")
    boolean exotic;

    public ProductItem(String name, String type, boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public ProductItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExotic() {
        return exotic;
    }

    public void setIsExotic(boolean exotic) {
        this.exotic = exotic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem item = (ProductItem) o;
        return exotic == item.exotic && Objects.equals(name, item.name) && Objects.equals(type, item.type);
    }

    @Override
    public String toString() {
        return "ProductItem {" +
                "name = '" + name + '\'' +
                ", type = '" + type + '\'' +
                ", exotic = " + exotic +
                '}';
    }
}
