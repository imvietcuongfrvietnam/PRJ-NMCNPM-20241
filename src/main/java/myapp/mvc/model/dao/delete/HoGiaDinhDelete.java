package myapp.mvc.model.dao.delete;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoGiaDinhDelete {
    public void delete(String maHoGiaDinh) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM hogiadinh WHERE MaHoGiaDinh = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
