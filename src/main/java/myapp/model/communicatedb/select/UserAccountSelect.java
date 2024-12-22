package myapp.model.communicatedb.select;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class UserAccountSelect {
    public int select() {
        String sql = "SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int password = 0;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

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
        return password;
    }
    public String getPasswordByUsername(String userName) {
        String password = null;
        String query = "SELECT MatKhau FROM taikhoannguoidung WHERE TenDangNhap = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("MatKhau");
            }
            else {
                System.out.println("No user found with username: " + userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }
    public static UserAccount getUserByUsername(String username) {
        UserAccount user = null;
        String query = "SELECT * FROM taikhoannguoidung WHERE TenDangNhap = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new UserAccount(
                        resultSet.getInt("MaTaiKhoan"),
                        resultSet.getString("VaiTro"),
                        resultSet.getString("TenDangNhap"),
                        resultSet.getString("MatKhau"),
                        resultSet.getString("HoTen"),
                        resultSet.getString("GioiTinh"),
                        resultSet.getString("NgaySinh"),
                        resultSet.getString("CCCD"),
                        resultSet.getString("SoDienThoai"),
                        resultSet.getString("Email"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("DiaChi")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
