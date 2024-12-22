package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleUpdate {
    public void update(Vehicle vehicle) {
                String query = "UPDATE quanlyphuongtien SET MaHoGiaDinh = ?, LoaiPhuongTien = ?, NgayBatDau = ?, NgayKetThuc = ?, GhiChu = ? WHERE BienSo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vehicle.getHouseHoldID());
            preparedStatement.setString(2, vehicle.getVehicleType());
            preparedStatement.setString(3, vehicle.getLicensePlate());
            preparedStatement.setDate(4, java.sql.Date.valueOf(vehicle.getStartDate()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(vehicle.getEndDate()));
            preparedStatement.setString(6, vehicle.getNote());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
