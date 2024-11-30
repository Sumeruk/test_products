package ru.Zinchenko.DB;

import java.sql.*;

public class JDBCImpl implements JDBC{
    static String url = "jdbc:h2:tcp://127.0.0.1:9092/mem:testdb";
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
