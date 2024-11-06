package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TaiKhoanNguoiDungInsert {
    public void insert(String VaiTro, String TenDangNhap, String MatKhau, LocalDateTime NgayTaoTaiKhoan) {
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO taikhoannguoidung (VaiTro, TenDangNhap, MatKhau, NgayTaoTaiKhoan) VALUES (?, ?, ?, ?)";

        try {
            connection = connector.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, VaiTro);
            preparedStatement.setString(2, TenDangNhap);
            preparedStatement.setString(3, MatKhau);

            // Chuyển đổi LocalDateTime sang Timestamp
            Timestamp timestamp = Timestamp.valueOf(NgayTaoTaiKhoan);
            preparedStatement.setTimestamp(4, timestamp);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã thêm: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connector.closeDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
