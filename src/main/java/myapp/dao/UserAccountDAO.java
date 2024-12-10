package myapp.dao;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;

import java.sql.*;
import java.time.LocalDateTime;

public class UserAccountDAO {
    public static void deleteByTenDangNhap(String tenDangNhap) {
        String sql = "DELETE FROM taikhoannguoidung WHERE TenDangNhap = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenDangNhap);

            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    public static void deleteByID(int maTaiKhoan) {
        String query = "DELETE FROM taikhoannguoidung WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy mã tài khoản cao nhất
    public static int selectMaTaiKhoan() {
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
    public static boolean isUserNameExist(String userName) {
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

    public static void insertUserAccount(String VaiTro, String TenDangNhap, String MatKhau, LocalDateTime NgayTaoTaiKhoan) {
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
            preparedStatement.setInt(5, 0);
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

    public static String selectByUsername(String userName) {
        String sql = "SELECT MatKhau FROM taikhoannguoidung WHERE TenDangNhap = ?";

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
                password = resultSet.getString("MatKhau");
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
        return password;
    }

    public static void updateUserAccount(UserAccount entity) {
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getVaiTro());
            preparedStatement.setString(2, entity.getTenDangNhap());
            preparedStatement.setString(3, entity.getMatKhau());
            preparedStatement.setInt(4, entity.getMaTaiKhoan());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Error Updating User Account", "An error occurred while updating the user account.", e.getMessage());
        }
    }

    public static void updatePasswordByEmail(String email, String password) {
        String query = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE MaTaiKhoan = (" +
                "SELECT MaTaiKhoan FROM thongtinnguoidung WHERE Email = ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Error Updating Password", "An error occurred while updating the password.", e.getMessage());
        }
    }

    public static void updatePasswordByUsername(String username, String password) {
        String query = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE TenDangNhap = ?";


        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Error Updating Password", "An error occurred while updating the password.", e.getMessage());
        }
    }
}
