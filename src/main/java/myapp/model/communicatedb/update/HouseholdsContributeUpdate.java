package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseholdsContribute;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HouseholdsContributeUpdate {
    public void update(HouseholdsContribute householdsContribute) {
        String query = "UPDATE danhsachhodonggop SET SoTienDongGop = ?, ThongTinBoSung = ?, DotDongGop = ?, NgayDongGop = ? WHERE MaHoDongGop = ? AND MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, householdsContribute.getSoTienDongGop());
            preparedStatement.setString(2, householdsContribute.getThongTinBoSung());
            preparedStatement.setString(3, householdsContribute.getDotDongGop());
            preparedStatement.setDate(4, java.sql.Date.valueOf(householdsContribute.getNgayDongGop()));
            preparedStatement.setString(5, householdsContribute.getMaHoDongGop());
            preparedStatement.setInt(6, householdsContribute.getMaQuy());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
