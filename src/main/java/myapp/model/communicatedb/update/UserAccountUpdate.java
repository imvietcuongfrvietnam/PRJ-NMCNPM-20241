package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAccountUpdate {

    public static void updateUser(UserAccount userAccount) {
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ?, HoTen = ?, GioiTinh = ?, NgaySinh = ?, CCCD = ?, SoDienThoai = ?, Email = ?, QueQuan = ?, DiaChi = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userAccount.getRole());
            preparedStatement.setString(2, userAccount.getUsername());
            preparedStatement.setString(3, userAccount.getPassword());
            preparedStatement.setString(4, userAccount.getName());
            preparedStatement.setString(5, userAccount.getGender());
            preparedStatement.setString(6, userAccount.getDateOfBirth());
            preparedStatement.setString(7, userAccount.getIDcard());
            preparedStatement.setString(8, userAccount.getPhone());
            preparedStatement.setString(9, userAccount.getEmail());
            preparedStatement.setString(10, userAccount.getHometown());
            preparedStatement.setString(11, userAccount.getAddress());
            preparedStatement.setInt(12, userAccount.getUserID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức cập nhật mật khẩu theo email
    public void updatePasswordByEmail(String email, String newPassword) {
        String query = "UPDATE taikhoannguoidung " +
                "SET MatKhau = ? " +
                "WHERE Email = ? ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword); // Mật khẩu mới
            preparedStatement.setString(2, email); // Email người dùng nhập vào

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("No matching user found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
