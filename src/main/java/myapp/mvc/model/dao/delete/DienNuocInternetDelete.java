package myapp.mvc.model.dao.delete;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DienNuocInternetDelete {
    public void delete(String maKHDien) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM diennuocinternet WHERE MaKHDien = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maKHDien);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
