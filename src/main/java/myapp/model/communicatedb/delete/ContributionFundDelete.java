package myapp.model.communicatedb.delete;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundDelete {
    public void delete(ContributionFund entity) {
        String query = "DELETE FROM bangquydonggop WHERE MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getFundID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
