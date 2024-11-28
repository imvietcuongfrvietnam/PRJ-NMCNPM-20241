package myapp.mvc.model.dao.delete;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ThongTinNguoiDungDelete {
    public void delete(String soCMND) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "DELETE FROM thongtinnguoidung WHERE SoCMND = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
