package myapp.model.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TaiKhoanNguoiDungInserter {
    public void insert(String VaiTro, String TenDangNhap, String MatKhau, Date NgayTaoTaiKhoan) {
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
            preparedStatement.setDate(4, new java.sql.Date(NgayTaoTaiKhoan.getTime())); // Chuyển đổi Date sang java.sql.Date

            int rowsAffected = preparedStatement.executeUpdate(); // Sử dụng executeUpdate() cho câu lệnh INSERT
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
