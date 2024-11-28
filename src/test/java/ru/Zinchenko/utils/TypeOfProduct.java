package ru.Zinchenko.utils;

public enum TypeOfProduct {
    FRUIT("Фрукт"),
    VEGETABLE("Овощ");

    public final String name;

    public String getName() {
        return name;
    }

    TypeOfProduct(String name){
        this.name = name;
    }
}
