package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAccountInsert {
    public void insert(String role, String username, String password, String name, String gender, String dateOfBirth, String IDcard, String phone, String email, String hometown, String address) {
        String query = "INSERT INTO taikhoannguoidung (VaiTro, TenDangNhap, MatKhau, HoTen, GioiTinh, NgaySinh, CCCD, SoDienThoai, Email, QueQuan, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, gender);
            preparedStatement.setDate(6, Date.valueOf(dateOfBirth));
            preparedStatement.setString(7, IDcard);
            preparedStatement.setString(8, phone);
            preparedStatement.setString(9, email);
            preparedStatement.setString(10, hometown);
            preparedStatement.setString(11, address);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
