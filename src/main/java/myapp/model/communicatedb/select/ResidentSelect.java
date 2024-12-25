package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResidentSelect {
    // Phương thức lấy tên người dân theo SoCMND
    public static String getResidentNameByResidentID(String residentID) {
        String residentName = "";
        String query = "SELECT HoTen FROM cudan WHERE CCCD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentName = resultSet.getString("HoTen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentName;
    }
    // Phương thức lấy số điện thoại người dân theo SoCMND
    public static String getResidentPhoneByResidentID(String residentID) {
        String residentPhone = null;
        String query = "SELECT SoDienThoai FROM cudan WHERE CCCD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentPhone = resultSet.getString("SoDienThoai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentPhone;
    }
    // Phương thức lấy thông tin chủ hộ gia đình theo MaCanHo
    public static Resident getResidentByApartmentID(String apartmentID) {
        Resident resident = null;
        String query = "SELECT TOP 1 HoTen, CCCD FROM cudan WHERE MaHoGiaDinh = (SELECT MaHoGiaDinh FROM hogiadinh WHERE MaCanHo = ?) ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String IDcard = resultSet.getString("CCCD");

                resident = new Resident(name, IDcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resident;
    }
    // Phương thức lấy tổng số cư dân
    public int getTotalResidents() {
        String query = "SELECT COUNT(CCCD) AS TotalResidents " +
                "FROM cudan ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("TotalResidents");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Phương thức lấy tổng số cư dân tạm trú
    public int getTemporaryResidents() {
        String query = "SELECT COUNT(CCCD) AS TemporaryResidents " +
                "FROM cudan " +
                "WHERE TrangThai = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "Tạm trú");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("TemporaryResidents");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Phương thức lấy tổng số cư dân tạm vắng
    public int getAbsentResidents() {
        String query = "SELECT COUNT(CCCD) AS AbsentResidents " +
                "FROM cudan " +
                "WHERE TrangThai = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "Tạm vắng");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("AbsentResidents");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
