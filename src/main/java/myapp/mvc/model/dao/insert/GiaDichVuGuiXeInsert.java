package myapp.mvc.model.dao.insert;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GiaDichVuGuiXeInsert {
    public void insert(String loaiXe, int giaGuiMotThang) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO giadichvuguixe (LoaiXe, GiaGuiMotThang) VALUES (?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loaiXe);
            preparedStatement.setInt(2, giaGuiMotThang);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
