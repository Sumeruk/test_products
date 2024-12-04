package ru.Zinchenko.DB.repository;

import ru.Zinchenko.DB.JDBC;
import ru.Zinchenko.DB.JDBCImpl;
import ru.Zinchenko.items.ProductItem;

import java.sql.*;

public class ProductRepository implements Repository{
    private static JDBC jdbc = JDBCImpl.getInstance();

    @Override
    public void add(ProductItem item) {
        String sql = "INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES ((?), (?), (?))";
        try (PreparedStatement statement = jdbc.getConnection().prepareStatement(sql)){
            statement.setString(1, item.getName());

            String type = "";
            if (item.getType().equals("Фрукт")){
                type = "FRUIT";
            } else if (item.getType().equals("Овощ")){
                type = "VEGETABLE";
            }
            statement.setString(2, type);

            int isExotic = (item.isExotic()) ? 1 : 0;
            statement.setInt(3, isExotic);

            statement.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public int getCountProductFounded(ProductItem item) {
        String sql = "SELECT * FROM FOOD WHERE" +
                " FOOD_NAME = (?) and FOOD_TYPE = (?) and FOOD_EXOTIC = (?);";
//        String sql = "SELECT COUNT(*) FROM FOOD WHERE" +
//                " FOOD_NAME = " + item.getName() +
//                " FOOD_TYPE = " + item.getType() +
//                " FOOD_EXOTIC = " + item.getType() + ";";
        try (PreparedStatement statement = jdbc.getConnection().prepareStatement(sql)){
            statement.setString(1, item.getName());

            String type = "";
            if (item.getType().equals("Фрукт")){
                type = "FRUIT";
            } else if (item.getType().equals("Овощ")){
                type = "VEGETABLE";
            }

            statement.setString(2, type);

            int isExotic = (item.isExotic()) ? 1 : 0;
            statement.setInt(3, isExotic);

            ResultSet res = statement.executeQuery();

            return getCountFromResultSet(res);
        } catch (SQLException sqlE){
            System.out.println(sqlE.getMessage());
        }
        return -1;
    }

    private int getCountFromResultSet(ResultSet res){
        int count = 0;
        try {
            while (res.next()) {
                count++;
            }
            return count;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return -1;
    }
}
