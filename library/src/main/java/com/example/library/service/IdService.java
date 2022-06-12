package com.example.library.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdService {
    private IdService(){}
    private static final IdService instance = new IdService();

    public static IdService getInstance() {
        return instance;
    }

    public int generateId() throws SQLException {
        ResultSet result = SqlService.getInstance().executeQuery("select id from library.last_id");
        if (!result.next()){
            PreparedStatement preparedStatement = SqlService.getInstance()
                    .prepareStatement("insert into library.last_id values(1)");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return 0;
        }
        else {
            int lastId = result.getInt("id");
            PreparedStatement preparedStatement = SqlService.getInstance()
                    .prepareStatement("UPDATE library.last_id" +
                            " SET id=? where id=?");
            preparedStatement.setInt(1,lastId+1);
            preparedStatement.setInt(2,lastId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return lastId;
        }
    }
}
