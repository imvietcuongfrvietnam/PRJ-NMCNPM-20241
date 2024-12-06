package myapp.model.dao.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleManagementInsert {
    public void insert(String bienSo, String loaiXe, String tenChuXe, String trangThai) {
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
}
