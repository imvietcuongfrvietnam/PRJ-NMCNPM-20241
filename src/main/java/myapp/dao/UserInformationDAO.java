package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;

import java.sql.*;

public class UserInformationDAO {

    public static void insertUserInfomation(String hoTen, String CMND, Date ngaySinh, String email, String queQuan, String soDienThoai, int maTaiKhoan, String diaChiThuongTru, String gioiTinh) {
        String sql = "INSERT INTO thongtinnguoidung (MaTaiKhoan, Ten, SoCMND, NgaySinh, Email, QueQuan, DienThoai, DiaChiThuongTru, GioiTinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.setString(2, hoTen);
            preparedStatement.setString(3, CMND);
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
            BaseDAO.showErrorAlert("Lỗi Thêm Thông Tin Người Dùng", "Không thể thêm thông tin người dùng mới", "Lỗi SQL: " + e.getMessage());
        }
    }

    public static UserInformation selectByUsername(String userName) {
        String sql = "SELECT SoCMND,Ten,Email,QueQuan,DienThoai,Ngay" +
                "Sinh, DiaChiThuongTru,GioiTinh FROM thongtinnguoidung WHERE MaTaiKhoan = (SELECT MaTaiKhoan FROM taikhoannguoidung WHERE TenDangNhap = ?)";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserInformation(
                            resultSet.getString("SoCMND"),
                            resultSet.getString("Ten"),
                            resultSet.getString("Email"),
                            resultSet.getString("QueQuan"),
                            resultSet.getString("DienThoai"),
                            resultSet.getDate("NgaySinh"),
                            resultSet.getString("DiaChiThuongTru"),
                            resultSet.getString("GioiTinh")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi Lấy Thông Tin Người Dùng", "Không thể lấy thông tin người dùng", "Lỗi SQL: " + e.getMessage());
        }
        return null;
    }

    public static boolean checkEmail(String email) {
        String sql = "SELECT * FROM thongtinnguoidung WHERE Email = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi Kiểm Tra Email", "Không thể kiểm tra email", "Lỗi SQL: " + e.getMessage());
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

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã cập nhật: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Thông Tin Người Dùng", "Không thể cập nhật thông tin người dùng", "Lỗi SQL: " + e.getMessage());
        }
    }

    public static void updateUserInformation(UserInformation entity, UserCredentials userCredentials) {
        String query = "UPDATE thongtinnguoidung SET Ten = ?, Email = ?, QueQuan = ?, DienThoai = ?, SoCMND = ?, GioiTinh = ?, DiaChiThuongTru = ? WHERE MaTaiKhoan = (SELECT MaTaiKhoan FROM taikhoannguoidung WHERE TenDangNhap = ?)";
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

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã cập nhật: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Thông Tin Người Dùng", "Không thể cập nhật thông tin người dùng", "Lỗi SQL: " + e.getMessage());
        }
    }

    public static void deleteBySoCMND(String soCMND) {
        String query = "DELETE FROM thongtinnguoidung WHERE SoCMND = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã xóa: " + rowsAffected);
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Xóa Thông Tin Người Dùng", "Không thể xóa thông tin người dùng", "Lỗi SQL: " + e.getMessage());
        }
    }
}
