package com.example.library.repository;

import com.example.library.model.Admin;
import com.example.library.service.SqlService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class AdminRepository implements Repository<Admin, Integer> {

    private AdminRepository(){}
    private static final AdminRepository instance = new AdminRepository();
    public static AdminRepository getInstance() {
        return instance;
    }

    @Override
    public Collection<Admin> findAll() throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.admins");
        HashSet<Admin> admins = new HashSet<>();
        while (result.next()) admins.add(parseAdmin(result));
        return new HashSet<>(admins);
    }

    @Override
    public Admin save(Admin admin) throws SQLException {
        PreparedStatement preparedStatement;

        if (findByUsername(admin.getUsername()).orElse(null) != null) {
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("UPDATE library.admins" +
                            " SET" +
                            " name = ?," +
                            " password= ?" +
                            " WHERE" +
                            " username = ?"
                    );
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getUsername());
        }
        else{
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("insert into library.admins values (?, ?, ?)");
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getUsername());
            preparedStatement.setString(3, admin.getPassword());
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return admin;
    }

    @Override
    public void delete(Admin admin) throws SQLException {
        PreparedStatement preparedStatement = SqlService.getInstance()
                .prepareStatement("DELETE FROM library.admins" +
                        " where" +
                        " username = '" + admin.getUsername() + "'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Optional<Admin> findById(Integer id) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.admins" +
                        " where" +
                        " id = '" + id + "'");

        return Optional.ofNullable(
                result.next()? parseAdmin(result):null
        );
    }

    public Optional<Admin> findByUsername(String username) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.admins" +
                        " where" +
                        " username = '" + username + "'");

        return Optional.ofNullable(
                result.next()? parseAdmin(result):null
        );
    }

    private Admin parseAdmin(ResultSet result) throws SQLException {
        Admin admin = new Admin();
        admin.setName(result.getString("name"));
        admin.setUsername(result.getString("username"));
        admin.setPassword(result.getString("password"));
        return admin;
    }
}
