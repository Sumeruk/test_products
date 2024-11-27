package ru.Zinchenko;

public class Main {
    public static void main(String[] args) {
        TestProducts testFruits =
                new TestAddToTable("банан", "Дуриан", "Фрукт", false, true);

        TestProducts testVegetables =
                new TestAddToTable("картофель", "Мандурия", "Овощ", false, true);

        System.out.println(testFruits.runTest());
        System.out.println(testVegetables.runTest());


    }
}