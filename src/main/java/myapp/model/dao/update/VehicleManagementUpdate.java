package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.VehicleManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleManagementUpdate implements Updater<VehicleManagement> {
    @Override
    public void update(VehicleManagement entity) {
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
