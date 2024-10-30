package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.GiaDichVu;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BangGiaDichVuUpdate implements Updater<GiaDichVu> {
    @Override
    public void update(GiaDichVu entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE banggiadichvu SET TenDichVu = ?, DonGia = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = connector.getConnection();
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
