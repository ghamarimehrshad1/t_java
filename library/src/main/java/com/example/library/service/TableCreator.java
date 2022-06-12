package com.example.library.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class TableCreator {
    private TableCreator(){}
    private static final TableCreator instance = new TableCreator();
    public static TableCreator getInstance() {
        return instance;
    }

    public void createTables() {

        try {
            Statement statement = SqlService.getInstance().createStatement();

            Set<String> tables = new HashSet<>();
            ResultSet rs = SqlService.getInstance().executeQuery("show tables");
            while (rs.next()) tables.add(rs.getString(1));

            if (!tables.contains("books")) {
                statement.executeUpdate("" +
                        " create TABLE books (" +
                        " id INT NOT NULL," +
                        " name VARCHAR(30) NOT NULL," +
                        " author VARCHAR(30) NOT NULL," +
                        " publication_name VARCHAR(30) NOT NULL," +
                        " circulation INT NOT NULL," +
                        " publishing_turn INT NOT NULL," +
                        " price INT NOT NULL," +
                        " admin_username VARCHAR(30)," +
                        " PRIMARY KEY (id));");
            }
            if (!tables.contains("users"))
                statement.executeUpdate("" +
                        " create TABLE users (" +
                        " full_name VARCHAR(30) NOT NULL," +
                        " national_code VARCHAR(30) NOT NULL," +
                        " father_name VARCHAR(30) NOT NULL," +
                        " address VARCHAR(30) NOT NULL," +
                        " PRIMARY KEY (national_code));");

            if (!tables.contains("admins"))
                statement.executeUpdate("" +
                        " create TABLE admins (" +
                        " name VARCHAR(30) NOT NULL," +
                        " username VARCHAR(30) NOT NULL," +
                        " password VARCHAR(30) NOT NULL," +
                        " PRIMARY KEY (username));");

            if (!tables.contains("user_books"))
                statement.executeUpdate("" +
                        " create TABLE user_books (" +
                        " id INT NOT NULL," +
                        " book_id INT NOT NULL," +
                        " national_code VARCHAR(30) NOT NULL," +
                        " borrowing_date DATE NOT NULL," +
                        " PRIMARY KEY (id));");

            if (!tables.contains("last_id"))
                statement.executeUpdate("" +
                        " create TABLE last_id (" +
                        " id INT NOT NULL," +
                        " PRIMARY KEY (id));");

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            SqlService.getInstance().close();
        }

    }
}
