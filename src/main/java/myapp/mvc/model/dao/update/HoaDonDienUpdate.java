package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.HoaDonDien;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonDienUpdate implements Updater<HoaDonDien> {
    @Override
    public void update(HoaDonDien entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE hoadondien SET ChiSoDienSuDung = ?, NgayHetHan = ?, TienDien = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getChiSoDienSuDung());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setBigDecimal(3, entity.getTienDien());
            preparedStatement.setString(4, entity.getThongTinBoSung());
            preparedStatement.setString(5, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
