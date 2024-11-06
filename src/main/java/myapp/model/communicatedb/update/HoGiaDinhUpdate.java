package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HoGiaDinh;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoGiaDinhUpdate implements Updater<HoGiaDinh> {
    @Override
    public void update(HoGiaDinh entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE hogiadinh SET MaPhongThue = ?, NgayChuyenVao = ?, NgayChuyenRa = ?, SoCMNDChuHo = ?, TrangThai = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Sử dụng các phương thức getter từ entity HoGiaDinh
            preparedStatement.setString(1, entity.getMaPhongThue()); // Cập nhật MaPhongThue
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayChuyenVao())); // Cập nhật NgayChuyenVao
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getNgayChuyenRa())); // Cập nhật NgayChuyenRa
            preparedStatement.setString(4, entity.getSoCMNDChuHo()); // Cập nhật SoCMNDChuHo
            preparedStatement.setString(5, entity.getTrangThai()); // Cập nhật TrangThai
            preparedStatement.setString(6, entity.getMaHoGiaDinh()); // Khóa chính để tìm bản ghi

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
