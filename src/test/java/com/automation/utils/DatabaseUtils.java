package com.automation.utils;

import java.sql.*;

public class DatabaseUtils {
        static String url=ConfigReader.getProperty("db.url");
        static String username=ConfigReader.getProperty("db.username");
        static String password=ConfigReader.getProperty("db.password");
        static Statement statement;

        public static void initDatabase() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();
            } catch (Exception exception) {
            }
        }
        public static ResultSet executeQuery(String query){
            ResultSet resultSet =null;
            try {
            resultSet = statement.executeQuery(query);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return resultSet;
        }
}
