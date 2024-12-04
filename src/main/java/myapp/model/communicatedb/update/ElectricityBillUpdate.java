package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ElectricityBill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ElectricityBillUpdate implements Updater<ElectricityBill> {
    @Override
    public void update(ElectricityBill entity) {
        String query = "UPDATE hoadondien SET ChiSoDienSuDung = ?, NgayHetHan = ?, TienDien = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
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
