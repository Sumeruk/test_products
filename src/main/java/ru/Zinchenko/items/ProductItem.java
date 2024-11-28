package ru.Zinchenko.items;

public class ProductItem {
    String name;
    String type;
    boolean isExotic;

    public ProductItem(String name, String type, boolean isExotic) {
        this.name = name;
        this.type = type;
        this.isExotic = isExotic;
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
        return isExotic;
    }

    public void setExotic(boolean exotic) {
        isExotic = exotic;
    }
}
