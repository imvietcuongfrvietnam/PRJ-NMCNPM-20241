package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ParkingBill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonGuiXeUpdate implements Updater<ParkingBill> {
    @Override
    public void update(ParkingBill entity) {

        String query = "UPDATE hoadonguixe SET SoTien = ?, NgayHetHan = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTien());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setString(3, entity.getThongTinBoSung());
            preparedStatement.setString(4, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
