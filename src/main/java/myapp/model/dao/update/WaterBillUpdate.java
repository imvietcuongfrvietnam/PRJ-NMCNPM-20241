package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.WaterBill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WaterBillUpdate implements Updater<WaterBill> {
    @Override
    public void update(WaterBill entity) {
        String query = "UPDATE hoadonnuoc SET NgayHetHan = ?, TienNuoc = ? WHERE MaHD = ? AND MaKH = ?";

        try (Connection connection = SQLConnector.getConnection();
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
