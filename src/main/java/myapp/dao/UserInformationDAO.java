package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;

import java.sql.*;

public class UserInformationDAO {
    public static void insertUserInfomation(String hoTen, String CMND, Date ngaySinh, String email, String queQuan, String soDienThoai, int maTaiKhoan, String diaChiThuongTru, String gioiTinh){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO thongtinnguoidung (MaTaiKhoan, Ten, SoCMND, NgaySinh, Email, QueQuan, DienThoai, DiaChiThuongTru, GioiTinh) VALUES (?, ?,?,?, ?, ?, ?, ?, ?)";

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.setString(2, hoTen);
            preparedStatement.setString(3, CMND);

            // Chuyển đổi ngaySinh từ String sang java.sql.Date
            preparedStatement.setDate(4, ngaySinh);

            preparedStatement.setString(5, email);
            preparedStatement.setString(6, queQuan);
            preparedStatement.setString(7, soDienThoai);
            preparedStatement.setString(8, diaChiThuongTru);
            preparedStatement.setString(9, gioiTinh);
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

    public static UserInformation selectByUsername(String userName){
        String sql = "SELECT * FROM thongtinnguoidung WHERE MaTaiKhoan = " +
                "SELECT MaTaiKhoan FROM taikhoannguoidung WHERE TenDangNhap = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String password = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new UserInformation(
                        resultSet.getString("SoCMND"),
                        resultSet.getString("Ten"),
                        resultSet.getString("Email"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("DienThoai"),
                        resultSet.getDate("NgaySinh"),
                        resultSet.getString("DiaChi"),
                        resultSet.getString("GioiTinh")

                );

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
        return null;
    }

    public static boolean checkEmail(String email){
        String sql = "SELECT * FROM thongtinnguoidung WHERE Email = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String password = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
               return true;
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
        return false;
    }

    public static void updateUserInformation(UserInformation entity) {
        String query = "UPDATE thongtinnguoidung SET Ten = ?, Email = ?, QueQuan = ?, DienThoai = ? WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTen());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getQueQuan());
            preparedStatement.setString(4, entity.getDienThoai());
            preparedStatement.setString(5, entity.getSoCMND());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserInformation(UserInformation entity, UserCredentials userCredentials) {
        String query = "UPDATE thongtinnguoidung SET Ten = ?, Email = ?, QueQuan = ?, DienThoai = ? , SoCMND = ?, GioiTinh = ?, DiaChiThuongTru = ? WHERE MaTaiKhoan =" +
                "SELECT MaTaiKhoan FROM taikhoannguoidung WHERE TenDangNhap = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTen());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getQueQuan());
            preparedStatement.setString(4, entity.getDienThoai());
            preparedStatement.setString(5, entity.getSoCMND());
            preparedStatement.setString(6, entity.getGioiTinh());
            preparedStatement.setString(7, entity.getDiaChi());
            preparedStatement.setString(8, userCredentials.getUsername());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteBySoCMND(String soCMND) {
        String query = "DELETE FROM thongtinnguoidung WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
