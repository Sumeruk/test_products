package ru.Zinchenko.DB;

import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.sql.*;

public class JDBCImpl implements JDBC{
    static String url = AppProperties.getProperty(PropConst.BASE_URL_DB);
    static String username = "user";
    static String password = "pass";
    private static Connection connection;
    private static JDBC object;

    public static JDBC getInstance(){
        if(object == null){
            System.out.println("DEBUG----add new jdbc instance");
            object = new JDBCImpl();
        }

        return object;
    }

    private JDBCImpl() {
    }

    public void connection(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection H2 successful");
        } catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        } catch (ClassNotFoundException clnfe){
            System.out.println("Cannot find the driver for connection");
        }

    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException sqle){
            System.out.println("Cannot close the connection");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
