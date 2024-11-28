package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.DanhSachHoDongGop;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DanhSachHoDongGopUpdate implements Updater<DanhSachHoDongGop> {
    @Override
    public void update(DanhSachHoDongGop entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE danhsachhodonggop SET SoTienDongGop = ?, ThongTinBoSung = ?, DotDongGop = ?, NgayDongGop = ? WHERE MaHoDongGop = ? AND MaQuy = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTienDongGop());
            preparedStatement.setString(2, entity.getThongTinBoSung());
            preparedStatement.setString(3, entity.getDotDongGop());
            preparedStatement.setDate(4, java.sql.Date.valueOf(entity.getNgayDongGop()));
            preparedStatement.setString(5, entity.getMaHoDongGop());
            preparedStatement.setInt(6, entity.getMaQuy());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
