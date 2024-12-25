package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HouseHoldInsert {
    public void insert(String houseHoldID, String apartmentID, String moveInDate, String moveOutDate, String residentID, String status) {
        String query = "INSERT INTO hogiadinh (MaHoGiaDinh, MaCanHo, NgayChuyenVao, NgayChuyenRa, CCCDChuHo, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            preparedStatement.setString(2, apartmentID);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.parse(moveInDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.parse(moveOutDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setString(5, residentID);
            preparedStatement.setString(6, status);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
