package myapp.mvc.model.dao.delete;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DanhSachHoDongGopDelete {
    public void delete(int ID) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM danhsachhodonggop WHERE ID = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
