package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ContributionFundInsert {
    public void insert(String fundID, String fundName, String amount, Date startDate, Date endDate) {
        String query = "INSERT INTO bangquydonggop (MaQuy, TenQuy, SoTien, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, fundID);
            preparedStatement.setString(2, fundName);
            preparedStatement.setString(3, amount);
            preparedStatement.setDate(4, startDate);
            preparedStatement.setDate(5, endDate);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
