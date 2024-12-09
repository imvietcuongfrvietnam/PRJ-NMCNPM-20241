package myapp.model.dao.select;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaTaiKhoanSelector {

    // Lấy mã tài khoản cao nhất
    public int select() {
        String sql = "SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int maxMaTaiKhoan = 0; // Thay đổi biến này thành maxMaTaiKhoan

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maxMaTaiKhoan = resultSet.getInt(1); // Lấy giá trị MAX(MaTaiKhoan)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
        return maxMaTaiKhoan; // Trả về giá trị MAX(MaTaiKhoan)
    }

    // Kiểm tra xem tên tài khoản đã tồn tại chưa
    public boolean isUserNameExist(String userName) {
        String sql = "SELECT COUNT(*) FROM taikhoannguoidung WHERE TenDangNhap = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            // Nếu kết quả COUNT(*) > 0 thì tài khoản đã tồn tại
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Trả về true nếu tên tài khoản tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
        return false; // Trả về false nếu tài khoản không tồn tại
    }
}
