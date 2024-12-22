package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundUpdate {
    public void update(ContributionFund contributionFund) {
        String query = "UPDATE bangquydonggop SET TenQuy = ?, SoTien = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, contributionFund.getFundName());
            preparedStatement.setString(2, contributionFund.getAmount());
            preparedStatement.setString(3, contributionFund.getStartDate());
            preparedStatement.setString(4, contributionFund.getEndDate());
            preparedStatement.setString(5, contributionFund.getFundID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
