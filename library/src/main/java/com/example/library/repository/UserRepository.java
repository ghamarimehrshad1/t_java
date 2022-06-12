package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.SqlService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class UserRepository implements Repository<User, Integer>{

    private UserRepository(){}
    private static final UserRepository instance = new UserRepository();
    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    public Collection<User> findAll() throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.users");

        HashSet<User> users = new HashSet<>();
        while (result.next()) users.add(parseUser(result));
        return users;
    }

    @Override
    public User save(User user) throws SQLException {
        PreparedStatement preparedStatement;
        if (findByNationalCode(user.getNational_code()).orElse(null) != null) {
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("UPDATE library.users" +
                            " SET" +
                            " full_name = ?," +
                            " father_name= ?," +
                            " address= ?" +
                            " WHERE" +
                            " national_code = ?"
                    );

            preparedStatement.setString(1, user.getFull_name());
            preparedStatement.setString(2, user.getFather_name());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getNational_code());
        }
        else{
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("insert into library.users values (?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFull_name());
            preparedStatement.setString(2, user.getNational_code());
            preparedStatement.setString(3, user.getFather_name());
            preparedStatement.setString(4, user.getAddress());
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return user;
    }

    @Override
    public void delete(User user) throws SQLException {
        PreparedStatement preparedStatement = SqlService.getInstance()
                .prepareStatement("DELETE FROM library.users" +
                        " where" +
                        " national_code= '" + user.getNational_code() + "'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Optional<User> findById(Integer id) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.users" +
                        " where" +
                        " id = '" + id + "'");

        return Optional.ofNullable(
                result.next()? parseUser(result):null
        );
    }

    public Optional<User> findByNationalCode(String national_code) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.users" +
                        " where" +
                        " national_code = '" + national_code + "'");

        return Optional.ofNullable(
                result.next()? parseUser(result):null
        );
    }

    public Collection<User> searchByNationalNum(String nationalNum) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.users" +
                        " where" +
                        " national_code like '%" + nationalNum + "%'");
        HashSet<User> users = new HashSet<>();
        while (result.next()) users.add(parseUser(result));
        return new HashSet<>(users);
    }


    private User parseUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setFull_name(result.getString("full_name"));
        user.setNational_code(result.getString("national_code"));
        user.setFather_name(result.getString("father_name"));
        user.setAddress(result.getString("address"));
        return user;
    }
}
