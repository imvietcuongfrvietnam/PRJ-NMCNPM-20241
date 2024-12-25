package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VehicleInsert {
    public void insert(String houseHoldID, String vehicleType, String licensePlate, String startDate, String endDate, String note) {
        String query = "INSERT INTO quanlyphuongtien (MaHoGiaDinh, LoaiPhuongTien, BienSo, NgayBatDau, NgayKetThuc, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            preparedStatement.setString(2, vehicleType);
            preparedStatement.setString(3, licensePlate);
            preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setString(6, note);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
