package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContributionFundDAO {
    public static void deleteByID(int ID) {
        String query = "DELETE FROM bangquydonggop WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateByMaQuy(ContributionFund entity) {
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

    public static void insertContributionFund(int id, String tenQuy, String moTa) {
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
