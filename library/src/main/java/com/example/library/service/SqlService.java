package com.example.library.service;

import java.sql.*;

public class SqlService {
    private Connection connection;
    private Statement statement;
    private SqlService(){}
    private static final SqlService instance = new SqlService();
    public static SqlService getInstance() {
        if (instance.connection == null){
            try {
                instance.connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/library?useSSL=true",
                        "root",
                        "M13811023"
                );
                instance.statement = instance.connection.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return instance;
    }
    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }
    public DatabaseMetaData getMetaDate () throws SQLException {
        return connection.getMetaData();
    }
    public void close() {
        try{
            connection.close();
            connection = null;
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
