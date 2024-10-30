package myapp.model.communicatedb.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuanLyXeDelete {
    public void delete(String bienSo) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM quanlyxe WHERE BienSo = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
