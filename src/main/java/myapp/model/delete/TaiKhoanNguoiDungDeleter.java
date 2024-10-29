package myapp.model.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaiKhoanNguoiDungDeleter {
    public void delete(String tenDangNhap) {
        String sql = "DELETE FROM taikhoannguoidung WHERE TenDangNhap = ?";

        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenDangNhap);

            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connector.closeDB();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
