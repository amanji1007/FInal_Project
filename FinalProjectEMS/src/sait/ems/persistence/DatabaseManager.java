package sait.ems.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager
{
    private static final String URL = "jdbc:mariadb://localhost:3306/ems_db";
    private static final String USER = "root";
    private static final String PASSWORD = "kumar786";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}