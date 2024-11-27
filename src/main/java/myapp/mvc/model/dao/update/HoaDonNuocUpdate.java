package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.HoaDonNuoc;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonNuocUpdate implements Updater<HoaDonNuoc> {
    @Override
    public void update(HoaDonNuoc entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE hoadonnuoc SET NgayHetHan = ?, TienNuoc = ? WHERE MaHD = ? AND MaKH = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setBigDecimal(2, entity.getTienNuoc());
            preparedStatement.setString(3, entity.getMaHD());
            preparedStatement.setString(4, entity.getMaKH());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
