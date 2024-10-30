package myapp.model.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonDienDelete {
    public void delete(String maHD) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM hoadondien WHERE MaHD = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
