package myapp.mvc.model.dao.select;

import myapp.mvc.model.connectdb.SQLConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MaTaiKhoanSelectorTest {

    private MaTaiKhoanSelector maTaiKhoanSelector;
    private SQLConnector sqlConnector;

    @BeforeEach
    void setUp() {
        // Tạo đối tượng SQLConnector
        sqlConnector = SQLConnector.getInstance();
        maTaiKhoanSelector = new MaTaiKhoanSelector();
    }

    @Test
    void testSelectMaxMaTaiKhoan() throws SQLException {
        // Thực hiện truy vấn
        String sql = "SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung";
        try (Connection connection = sqlConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Kiểm tra kết quả
            if (resultSet.next()) {
                int maxMaTaiKhoan = resultSet.getInt(1); // Lấy giá trị MAX(MaTaiKhoan)
                assertTrue(maxMaTaiKhoan > 0, "Giá trị MaTaiKhoan phải lớn hơn 0");
                System.out.println("Giá trị MAX(MaTaiKhoan): " + maxMaTaiKhoan);
            } else {
                fail("Không có kết quả trả về từ cơ sở dữ liệu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Có lỗi xảy ra khi thực hiện truy vấn SQL");
        }
    }
}
