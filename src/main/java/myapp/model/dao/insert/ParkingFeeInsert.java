package myapp.model.dao.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ParkingFeeInsert {
    public void insert(String loaiXe, int giaGuiMotThang) {
        String query = "INSERT INTO phiguixe (LoaiXe, GiaGuiMotThang) VALUES (?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loaiXe);
            preparedStatement.setInt(2, giaGuiMotThang);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
