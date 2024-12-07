package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundUpdate implements Updater<ContributionFund> {
    @Override
    public void update(ContributionFund entity) {
        String query = "UPDATE quydonggop SET TenQuy = ?, SoTien = ?, DotThuPhi = ? WHERE MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getFundName());
            preparedStatement.setString(2, entity.getFundID());
            preparedStatement.setString(3, entity.getAmount());
            preparedStatement.setString(4, entity.getPeriodOfTime());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
