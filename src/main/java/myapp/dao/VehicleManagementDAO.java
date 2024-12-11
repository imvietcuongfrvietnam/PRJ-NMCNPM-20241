package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.controller.BaseController;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Vehicle;
import myapp.model.entities.entitiesdb.VehicleManagement;

import java.sql.*;

import static myapp.db.SQLConnector.getConnection;

public class VehicleManagementDAO {
    public static void insertVehicleManagement(String bienSo, String loaiXe, String tenChuXe, String trangThai) {
        String query = "INSERT INTO quanlyxe (BienSo, LoaiXe, TenChuXe, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.setString(2, loaiXe);
            preparedStatement.setString(3, tenChuXe);
            preparedStatement.setString(4, trangThai);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateVehicleManagement(VehicleManagement entity) {
                String query = "UPDATE quanlyxe SET LoaiPhuongTien = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE BienSo = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getLoaiPhuongTien());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayBatDau()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getNgayKetThuc()));
            preparedStatement.setString(4, entity.getBienSo());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteVehicle(String bienSo) {
        String query = "DELETE FROM quanlyxe WHERE BienSo = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyxe";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Date startDate = resultSet.getDate("NgayBatDau");
                Date endDate = resultSet.getDate("NgayKetThuc");

                vehiclesList.add(new Vehicle(
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("LoaiPhuongTien"),
                        resultSet.getString("BienSo"),
                       startDate, endDate,
                        resultSet.getString("GhiChu")
                ));
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách xe", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy danh sách phuong tiện");
        }
        return vehiclesList;
    }

}
