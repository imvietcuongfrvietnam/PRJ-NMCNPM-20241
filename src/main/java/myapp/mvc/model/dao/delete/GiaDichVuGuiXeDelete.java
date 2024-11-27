package myapp.mvc.model.dao.delete;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GiaDichVuGuiXeDelete {
    public void delete(String loaiXe) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM giadichvuguixe WHERE LoaiXe = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loaiXe);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
