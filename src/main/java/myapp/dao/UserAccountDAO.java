package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;
import myapp.model.entities.entitiessystem.UserCredentials;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Lớp UserAccountDAO cung cấp các phương thức để thực hiện các thao tác CRUD (Create, Read, Update, Delete)
 * đối với bảng `taikhoannguoidung` trong cơ sở dữ liệu.
 */
public class UserAccountDAO {

    /**
     * Xóa tài khoản người dùng dựa trên tên đăng nhập.
     *
     * @param tenDangNhap tên đăng nhập của tài khoản cần xóa.
     */
    public static void deleteByTenDangNhap(String tenDangNhap) {
        String sql = "DELETE FROM taikhoannguoidung WHERE TenDangNhap = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenDangNhap);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Xóa Tài Khoản", "Không thể xóa tài khoản người dùng", "Lỗi SQL: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    SQLConnector.closeConnection();
                }
            } catch (SQLException e) {
                BaseDAO.showErrorAlert("Lỗi Đóng Kết Nối", "Không thể đóng kết nối cơ sở dữ liệu", "Lỗi SQL: " + e.getMessage());
            }
        }
    }

    /**
     * Xóa tài khoản người dùng dựa trên mã tài khoản.
     *
     * @param maTaiKhoan mã tài khoản cần xóa.
     */
    public static void deleteByID(int maTaiKhoan) {
        String query = "DELETE FROM taikhoannguoidung WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Xóa Tài Khoản", "Không thể xóa tài khoản người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Xóa Tài Khoản", "Có lỗi xảy ra khi xóa tài khoản", "Lỗi: " + e.getMessage());
        }
    }

    /**
     * Lấy mã tài khoản cao nhất từ bảng `taikhoannguoidung`.
     *
     * @return mã tài khoản cao nhất.
     */
    public static int selectMaTaiKhoan() {
        String sql = "SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int maxMaTaiKhoan = 0;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maxMaTaiKhoan = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Mã Tài Khoản", "Không thể lấy mã tài khoản cao nhất", "Lỗi SQL: " + e.getMessage());
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
                BaseDAO.showErrorAlert("Lỗi Đóng Kết Nối", "Không thể đóng kết nối cơ sở dữ liệu", "Lỗi SQL: " + e.getMessage());
            }
        }
        return maxMaTaiKhoan;
    }

    /**
     * Kiểm tra xem tên tài khoản người dùng đã tồn tại trong cơ sở dữ liệu hay chưa.
     *
     * @param userName tên đăng nhập cần kiểm tra.
     * @return true nếu tài khoản đã tồn tại, false nếu chưa tồn tại.
     */
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

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Kiểm Tra Tài Khoản", "Không thể kiểm tra tài khoản người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Kiểm Tra Tài Khoản", "Có lỗi xảy ra khi kiểm tra tài khoản", "Lỗi: " + e.getMessage());
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
                BaseDAO.showErrorAlert("Lỗi Đóng Kết Nối", "Không thể đóng kết nối cơ sở dữ liệu", "Lỗi SQL: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Thêm một tài khoản người dùng mới vào cơ sở dữ liệu.
     *
     * @param VaiTro vai trò của người dùng.
     * @param TenDangNhap tên đăng nhập của người dùng.
     * @param MatKhau mật khẩu của người dùng.
     * @param NgayTaoTaiKhoan thời gian tạo tài khoản.
     */
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
            BaseDAO.showErrorAlert("Lỗi Thêm Tài Khoản", "Không thể thêm tài khoản người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Tài Khoản", "Có lỗi xảy ra khi thêm tài khoản", "Lỗi: " + e.getMessage());
        }
    }

    /**
     * Lấy mật khẩu của người dùng dựa trên tên đăng nhập.
     *
     * @param userName tên đăng nhập của người dùng.
     * @return mật khẩu của người dùng, nếu không tìm thấy trả về null.
     */
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
            BaseDAO.showErrorAlert("Lỗi Lấy Mật Khẩu", "Không thể lấy mật khẩu của tài khoản", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Mật Khẩu", "Có lỗi xảy ra khi lấy mật khẩu", "Lỗi: " + e.getMessage());
        }

        return password;
    }

    /**
     * Cập nhật thông tin tài khoản người dùng.
     *
     * @param entity đối tượng UserAccount chứa thông tin tài khoản người dùng cần cập nhật.
     */
    public static void updateUserAccount(UserAccount entity) {
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getVaiTro());
            preparedStatement.setString(2, entity.getTenDangNhap());
            preparedStatement.setString(3, entity.getMatKhau());
            preparedStatement.setInt(4, entity.getMaTaiKhoan());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Tài Khoản", "Không thể cập nhật tài khoản người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Tài Khoản", "Có lỗi xảy ra khi cập nhật tài khoản", "Lỗi: " + e.getMessage());
        }
    }

    /**
     * Cập nhật mật khẩu người dùng dựa trên email.
     *
     * @param email email của người dùng.
     * @param password mật khẩu mới.
     */
    public static void updatePasswordByEmail(String email, String password) {
        String query = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE MaTaiKhoan = (" +
                "SELECT MaTaiKhoan FROM thongtinnguoidung WHERE Email = ?)" ;

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Mật Khẩu", "Không thể cập nhật mật khẩu của người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Mật Khẩu", "Có lỗi xảy ra khi cập nhật mật khẩu", "Lỗi: " + e.getMessage());
        }
    }

    /**
     * Cập nhật mật khẩu người dùng dựa trên tên đăng nhập.
     *
     * @param username tên đăng nhập của người dùng.
     * @param password mật khẩu mới.
     */
    public static void updatePasswordByUsername(String username, String password) {
        String query = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE TenDangNhap = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Mật Khẩu", "Không thể cập nhật mật khẩu của người dùng", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật Mật Khẩu", "Có lỗi xảy ra khi cập nhật mật khẩu", "Lỗi: " + e.getMessage());
        }
    }
    public static void loginSuccessfully(String username){
        String query = "UPDATE taikhoannguoidung SET TinhTrang = 1 WHERE TenDangNhap = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Không thể cập nhật trạng thái", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Có lỗi xảy ra khi cập nhật trạng thái", "Lỗi: " + e.getMessage());
        }
    }
    public static void logoutSuccessfully(String username){
        String query = "UPDATE taikhoannguoidung SET TinhTrang = 0 WHERE TenDangNhap = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Không thể cập nhật trạng thái", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Có lỗi xảy ra khi cập nhật trạng thái", "Lỗi: " + e.getMessage());
        }
    }
    public static int checkTinhTrang(String username) {
        String query = "SELECT TinhTrang FROM taikhoannguoidung WHERE TenDangNhap = ?";
        int tinhTrang = -1; // Giá trị mặc định nếu không tìm thấy TinhTrang

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Kiểm tra xem có kết quả trả về không
                if (resultSet.next()) {
                    tinhTrang = resultSet.getInt("TinhTrang"); // Lấy giá trị của cột TinhTrang
                }
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Không thể cập nhật trạng thái", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Cập Nhật trạng thái", "Có lỗi xảy ra khi cập nhật trạng thái", "Lỗi: " + e.getMessage());
        }

        return tinhTrang; // Trả về giá trị TinhTrang hoặc -1 nếu không tìm thấy
    }

}