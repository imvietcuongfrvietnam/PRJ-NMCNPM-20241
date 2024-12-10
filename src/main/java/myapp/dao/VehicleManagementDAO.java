package myapp.dao;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.VehicleManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleManagementDAO {
    public static void insertVehicleManagement(String bienSo, String loaiXe, String tenChuXe, String trangThai) {
        String query = "INSERT INTO quanlyxe (BienSo, LoaiXe, TenChuXe, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
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

        try (Connection connection = SQLConnector.getConnection();
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
}
