package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ServiceBill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServiceBillUpdate implements Updater<ServiceBill> {
    @Override
    public void update(ServiceBill entity) {
        String query = "UPDATE hoadondichvu SET SoTien = ?, NgayHetHan = ?, ThongTinBoSung = ? WHERE MaHD = ? AND MaDichVu = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTien());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setString(3, entity.getThongTinBoSung());
            preparedStatement.setString(4, entity.getMaHD());
            preparedStatement.setInt(5, entity.getMaDichVu());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
