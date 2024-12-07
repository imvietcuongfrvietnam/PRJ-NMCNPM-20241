package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HouseHoldUpdate implements Updater<HouseHold> {
    @Override
    public void update(HouseHold entity) {
                String query = "UPDATE hogiadinh SET MaCanHo = ?, NgayChuyenVao = ?, NgayChuyenRa = ?, SoCMNDChuHo = ?, TrangThai = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Sử dụng các phương thức getter từ entity HoGiaDinh
            preparedStatement.setString(1, entity.getApartmentID()); // Cập nhật MaCanHo
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getMoveInDate())); // Cập nhật NgayChuyenVao
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getMoveOutDate())); // Cập nhật NgayChuyenRa
            preparedStatement.setString(4, entity.getResidentID()); // Cập nhật SoCMNDChuHo
            preparedStatement.setString(5, entity.getStatus()); // Cập nhật TrangThai
            preparedStatement.setString(6, entity.getHouseHoldID()); // Khóa chính để tìm bản ghi

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
