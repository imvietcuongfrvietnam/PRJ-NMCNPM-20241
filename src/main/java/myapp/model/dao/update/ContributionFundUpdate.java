package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundUpdate implements Updater<ContributionFund> {
    @Override
    public void update(ContributionFund entity) {
        String query = "UPDATE bangquydonggop SET TenQuy = ?, MoTa = ? WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTenQuy());
            preparedStatement.setString(2, entity.getMoTa());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
