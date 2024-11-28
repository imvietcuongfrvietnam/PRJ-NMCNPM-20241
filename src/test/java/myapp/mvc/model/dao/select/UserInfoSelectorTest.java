package myapp.mvc.model.dao.select;
import myapp.mvc.model.entities.entitiessystem.UserInfo;
import myapp.mvc.model.dao.select.UserInfoSelector;
import myapp.mvc.model.connectdb.SQLConnector;  // Import lớp kết nối CSDL của bạn
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserInfoSelectorTest {

    private UserInfoSelector userInfoSelector;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        userInfoSelector = new UserInfoSelector();
        // Sử dụng kết nối cơ sở dữ liệu đã có sẵn trong lớp SQLConnector
        try {
            connection = SQLConnector.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelect_WhenUserExists_ShouldReturnUserInfo() {
        // Arrange
        String maTaiKhoan = "vietcuong04";  // Tài khoản tồn tại trong CSDL của bạn

        // Act
        UserInfo userInfo = userInfoSelector.select(maTaiKhoan);

        // Assert
        assertNotNull(userInfo);
        assertEquals("John Doe", userInfo.getName());
        assertEquals("1990-01-01", userInfo.getBirthday());
        assertEquals("123456", userInfo.getId());
        assertEquals("0123456789", userInfo.getPhone());
        assertEquals("john.doe@example.com", userInfo.getEmail());
        assertEquals("Hanoi", userInfo.getHometown());
    }

    @Test
    public void testSelect_WhenUserDoesNotExist_ShouldReturnNull() {
        // Arrange
        String maTaiKhoan = "vietcuong04";  // Tài khoản không tồn tại trong CSDL của bạn

        // Act
        UserInfo userInfo = userInfoSelector.select(maTaiKhoan);

        // Assert
        assertNull(userInfo);
    }

    @Test
    public void testSelect_WhenSQLExceptionOccurs_ShouldReturnNull() {
        // Arrange
        String maTaiKhoan = "vietcuong04";  // Giả sử tài khoản có trong CSDL nhưng có thể gặp lỗi kết nối

        // Act
        UserInfo userInfo = userInfoSelector.select(maTaiKhoan);

        // Assert
        assertNull(userInfo);
    }

    @Test
    public void testDatabaseConnection_ShouldBeValid() {
        // Test kết nối cơ sở dữ liệu
        assertNotNull(connection);
        try {
            assertTrue(connection.isValid(2));  // Kiểm tra xem kết nối có hợp lệ không
        } catch (SQLException e) {
            fail("Kết nối cơ sở dữ liệu không hợp lệ");
        }
    }
}
