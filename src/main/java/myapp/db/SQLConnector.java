package myapp.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.dao.BaseDAO;
import myapp.model.entities.Fee;

import java.sql.*;

public class SQLConnector {

    // Thông tin kết nối cơ sở dữ liệu
    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-6EEOSOCP\\SQLEXPRESS\\SQLEXPRESS:1433;databaseName=QlThuphi;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456789";
    private static Connection connection;

    // Lấy kết nối duy nhất
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return connection;
    }

    // Đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi khi đóng cơ sở du liệu,", "Có lỗi xảy ra", "Có lỗi khi tiến hành đóng kết nối cơ sở dữ liệu");
        }
    }

    public static ObservableList<Fee> getFees() {
        ObservableList<Fee> feesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlykhoanphi";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                feesList.add(new Fee(
                        resultSet.getString("MaKhoanPhi"),
                        resultSet.getString("TenKhoanPhi"),
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("SoTien"),
                        resultSet.getDate("NgayHetHan"),
                        resultSet.getString("TinhTrang"),
                        resultSet.getString("GhiChu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feesList;
    }
}
