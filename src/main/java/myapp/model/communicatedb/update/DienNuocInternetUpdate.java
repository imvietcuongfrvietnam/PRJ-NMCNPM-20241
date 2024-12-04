package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.DienNuocInternet;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DienNuocInternetUpdate implements Updater<DienNuocInternet> {
    @Override
    public void update(DienNuocInternet entity) {
                String query = "UPDATE diennuocinternet SET NCCDien = ?, NCCNuoc = ?, NCCInternet = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getNCCDien());
            preparedStatement.setString(2, entity.getNCCNuoc());
            preparedStatement.setString(3, entity.getNCCInternet());
            preparedStatement.setString(4, entity.getMaHoGiaDinh());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
