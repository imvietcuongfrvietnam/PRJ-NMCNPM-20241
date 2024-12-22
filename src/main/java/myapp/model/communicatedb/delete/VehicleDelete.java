package myapp.model.communicatedb.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleDelete {
    public void delete(String bienSo) {
        String query = "DELETE FROM quanlyphuongtien WHERE BienSo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
