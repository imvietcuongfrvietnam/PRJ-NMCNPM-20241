package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleUpdate implements Updater<Vehicle> {
    @Override
    public void update(Vehicle entity) {
                String query = "UPDATE quanlyphuongtien SET MaHoGiaDinh = ?, LoaiPhuongTien = ?, NgayBatDau = ?, NgayKetThuc = ?, GhiChu = ? WHERE BienSo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getHouseHoldID());
            preparedStatement.setString(2, entity.getVehicleType());
            preparedStatement.setString(3, entity.getLicensePlate());
            preparedStatement.setDate(4, java.sql.Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(entity.getEndDate()));
            preparedStatement.setString(6, entity.getNote());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
