package ru.Zinchenko.DB.repository;

import ru.Zinchenko.items.ProductItem;

public interface Repository {
    void add(ProductItem item);

    int getCountProductFounded(ProductItem item);


}
