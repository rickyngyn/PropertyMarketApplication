package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
    final String dbURL = "jdbc:mysql://localhost:3306/propertymarket";
    final String USERNAME = "root";
    final String PASSWORD = "admin";

    public static Connection getConection() {
        Connection connection = null;
        final String dbURL = "jdbc:mysql://localhost:3306/propertymarket";
        final String USERNAME = "root";
        final String PASSWORD = "admin";
        try {
            connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
