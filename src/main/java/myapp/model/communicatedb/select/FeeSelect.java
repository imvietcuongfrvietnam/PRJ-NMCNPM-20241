package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FeeSelect {
    // Phương thức lấy tổng phí dịch vụ
    public double getTotalServiceFee() {
        String query = "SELECT SUM(SoTien) AS TotalServiceFee " +
                "FROM quanlykhoanphi " +
                "WHERE MaKhoanPhi = ? AND TinhTrang = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "PDV");
            preparedStatement.setString(2, "Đã thanh toán");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("TotalServiceFee");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Phương thức lấy tổng phí quản lý
    public double getTotalManagementFee() {
        String query = "SELECT SUM(SoTien) AS TotalManagementFee " +
                "FROM quanlykhoanphi " +
                "WHERE MaKhoanPhi = ? AND TinhTrang = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "PQL");
            preparedStatement.setString(2, "Đã thanh toán");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("TotalManagementFee");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Phương thức lấy tổng phí gửi xe
    public double getTotalParkingFee() {
        String query = "SELECT SUM(SoTien) AS TotalParkingFee " +
                "FROM quanlykhoanphi " +
                "WHERE MaKhoanPhi IN (?, ?) AND TinhTrang = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thay đổi tham số trong PreparedStatement
            preparedStatement.setString(1, "PGXM");
            preparedStatement.setString(2, "PGXOT");
            preparedStatement.setString(3, "Đã thanh toán");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("TotalParkingFee");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Phương thức lấy tổng các quỹ đóng góp
    public double getTotalContributionFund() {
        String query = "SELECT SUM(SoTienDongGop) AS TotalContributionFund " +
                "FROM quanlyquydonggop ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("TotalContributionFund");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Phương thức lấy đơn giá khoản phí theo MaKhoanPhi
    public String getFee(String feeID) {
        String feeValue = null;
        String query = "SELECT DonGia FROM banggiakhoanphi WHERE MaKhoanPhi = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, feeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                feeValue = resultSet.getString("DonGia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feeValue;
    }
}
