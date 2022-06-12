package com.example.library.repository;

import com.example.library.model.UserBook;
import com.example.library.service.IdService;
import com.example.library.service.SqlService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class UserBookRepository implements Repository<UserBook, Integer>{

    private UserBookRepository(){}
    private static final UserBookRepository instance = new UserBookRepository();
    public static UserBookRepository getInstance() {
        return instance;
    }

    @Override
    public Collection<UserBook> findAll() throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.user_books");

        HashSet<UserBook> relations = new HashSet<>();
        while (result.next()) relations.add(parseRelation(result));
        return relations;
    }

    @Override
    public UserBook save(UserBook relation) throws SQLException {
        PreparedStatement preparedStatement;

        if (relation.getId() != null) {
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("UPDATE library.user_books" +
                            " SET" +
                            " book_id = ?," +
                            " national_code= ?" +
                            " borrowing_date= ?" +
                            " WHERE" +
                            " id = ?"
                    );
            preparedStatement.setInt(1, relation.getBook_id());
            preparedStatement.setString(2, relation.getNational_code());
            preparedStatement.setDate(3, relation.getBorrowing_date());
            preparedStatement.setInt(4,relation.getId());
        }
        else{
            preparedStatement = SqlService.getInstance()
                    .prepareStatement("insert into library.user_books values (?, ?, ?, ?)");
            int id = IdService.getInstance().generateId();
            relation.setId(id);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, relation.getBook_id());
            preparedStatement.setString(3, relation.getNational_code());
            preparedStatement.setDate(4, relation.getBorrowing_date());
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return relation;
    }

    @Override
    public void delete(UserBook relation) throws SQLException {
        PreparedStatement preparedStatement = SqlService.getInstance()
                .prepareStatement("DELETE FROM library.user_books" +
                        " where" +
                        " id = '" + relation.getId() + "'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Optional<UserBook> findById(Integer id) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.user_books" +
                        " where" +
                        " id = '" + id + "'");

        return Optional.ofNullable(
                result.next()? parseRelation(result):null
        );
    }

    public Optional<UserBook> findByBookId(Integer id) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.user_books" +
                        " where" +
                        " book_id = '" + id + "'");

        return Optional.ofNullable(
                result.next()? parseRelation(result):null
        );
    }

    public Optional<UserBook> findByNationalCodeAndBookId(String national_code, Integer bookId) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.user_books where" +
                        " national_code = '" + national_code + "'" +
                        " and" +
                        " book_id = '" + bookId + "'");

        return Optional.ofNullable(
                result.next()? parseRelation(result):null
        );
    }

    public Collection<UserBook> findAllByNationalCode(String national_code) throws SQLException {
        ResultSet result = SqlService.getInstance()
                .executeQuery("select * from library.user_books" +
                        " where" +
                        " national_code = '" + national_code + "'");

        HashSet<UserBook> relations = new HashSet<>();
        while (result.next()) relations.add(parseRelation(result));
        return relations;
    }

    private UserBook parseRelation(ResultSet result) throws SQLException {
        UserBook relation = new UserBook();
        relation.setId(result.getInt("id"));
        relation.setBook_id(result.getInt("book_id"));
        relation.setNational_code(result.getString("national_code"));
        relation.setBorrowing_date(result.getDate("borrowing_date"));
        return relation;
    }
}
