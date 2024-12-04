package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ServicePrice;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServicePriceUpdate implements Updater<ServicePrice> {
    @Override
    public void update(ServicePrice entity) {
        String query = "UPDATE banggiadichvu SET TenDichVu = ?, DonGia = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTenDichVu());
            preparedStatement.setBigDecimal(2, entity.getDonGia());
            preparedStatement.setString(3, entity.getThongTinBoSung());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
