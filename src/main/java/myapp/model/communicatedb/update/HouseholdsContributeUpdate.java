package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseholdsContribute;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HouseholdsContributeUpdate implements Updater<HouseholdsContribute> {
    @Override
    public void update(HouseholdsContribute entity) {
        String query = "UPDATE danhsachhodonggop SET SoTienDongGop = ?, ThongTinBoSung = ?, DotDongGop = ?, NgayDongGop = ? WHERE MaHoDongGop = ? AND MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
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
