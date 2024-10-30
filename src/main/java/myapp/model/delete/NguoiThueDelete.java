package myapp.model.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NguoiThueDelete {
    public void delete(String soCMND) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM nguoithue WHERE SoCMND = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
