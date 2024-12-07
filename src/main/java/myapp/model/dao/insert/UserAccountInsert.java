package myapp.model.dao.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserAccountInsert {
    int defaultTinhTrang = 0;
    public void insert(String VaiTro, String TenDangNhap, String MatKhau, LocalDateTime NgayTaoTaiKhoan) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO taikhoannguoidung (VaiTro, TenDangNhap, MatKhau, NgayTaoTaiKhoan, TinhTrang) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = SQLConnector.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, VaiTro);
            preparedStatement.setString(2, TenDangNhap);
            preparedStatement.setString(3, MatKhau);

            // Chuyển đổi LocalDateTime sang Timestamp
            Timestamp timestamp = Timestamp.valueOf(NgayTaoTaiKhoan);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setInt(5, defaultTinhTrang);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã thêm: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                SQLConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
