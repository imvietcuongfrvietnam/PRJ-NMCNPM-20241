package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HouseHoldUpdate{
    public void update(HouseHold houseHold) {
                String query = "UPDATE hogiadinh SET MaCanHo = ?, NgayChuyenVao = ?, NgayChuyenRa = ?, SoCMNDChuHo = ?, TrangThai = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Sử dụng các phương thức getter từ houseHold HoGiaDinh
            preparedStatement.setString(1, houseHold.getApartmentID()); // Cập nhật MaCanHo
            preparedStatement.setDate(2, java.sql.Date.valueOf(houseHold.getMoveInDate())); // Cập nhật NgayChuyenVao
            preparedStatement.setDate(3, java.sql.Date.valueOf(houseHold.getMoveOutDate())); // Cập nhật NgayChuyenRa
            preparedStatement.setString(4, houseHold.getResidentID()); // Cập nhật SoCMNDChuHo
            preparedStatement.setString(5, houseHold.getStatus()); // Cập nhật TrangThai
            preparedStatement.setString(6, houseHold.getHouseHoldID()); // Khóa chính để tìm bản ghi

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
