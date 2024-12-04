package ru.Zinchenko.tests.cucTests;

import io.cucumber.datatable.DataTable;
import ru.Zinchenko.items.ProductItem;

import java.util.Locale;

public class ProductTableTransformer {

    public static ProductItem transform(DataTable dataTable) {
        return new ProductItem(
                dataTable.cells().get(1).get(0),
                dataTable.cells().get(1).get(1),
                Boolean.parseBoolean(dataTable.cells().get(1).get(2)));
    }
}
