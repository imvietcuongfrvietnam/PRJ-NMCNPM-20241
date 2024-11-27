package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.HoaDonGuiXe;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonGuiXeUpdate implements Updater<HoaDonGuiXe> {
    @Override
    public void update(HoaDonGuiXe entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE hoadonguixe SET SoTien = ?, NgayHetHan = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = connector.getConnection();
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
