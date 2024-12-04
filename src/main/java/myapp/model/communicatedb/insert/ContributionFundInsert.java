package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundInsert {
    public void insert(int id, String tenQuy, String moTa) {
                String query = "INSERT INTO bangquydonggop (ID, TenQuy, MoTa) VALUES (?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, tenQuy);
            preparedStatement.setString(3, moTa);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
