package myapp.model.update;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordUpdater implements model.update.Updater {
    public void update(String userName, String newPassword) {
        // Câu lệnh SQL để cập nhật mật khẩu
        String sql = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE MaTaiKhoan = ?";

        // Kết nối và PreparedStatement
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            connection = connector.getConnection();

            // Tạo PreparedStatement từ câu lệnh SQL
            preparedStatement = connection.prepareStatement(sql);

            // Gán giá trị cho các tham số trong câu lệnh SQL
            preparedStatement.setString(1, newPassword); // Thay thế ? đầu tiên bằng newPassword
            preparedStatement.setString(2, userName);    // Thay thế ? thứ hai bằng userName

            // Thực thi câu lệnh UPDATE
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã cập nhật: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng PreparedStatement và kết nối trong khối finally
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
