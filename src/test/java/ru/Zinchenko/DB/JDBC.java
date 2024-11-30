package ru.Zinchenko.DB;

import java.sql.Connection;

public interface JDBC {
    void connection();
    void closeConnection();
    Connection getConnection();
}
