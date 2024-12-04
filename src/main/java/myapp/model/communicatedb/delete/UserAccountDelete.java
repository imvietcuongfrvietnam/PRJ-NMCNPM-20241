package myapp.model.communicatedb.delete;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAccountDelete {
    public void delete(String tenDangNhap) {
        String sql = "DELETE FROM taikhoannguoidung WHERE TenDangNhap = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = SQLConnector.getConnection();
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
                    SQLConnector.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void delete(int maTaiKhoan) {
        String query = "DELETE FROM taikhoannguoidung WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
