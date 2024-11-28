package myapp.mvc.model.connectdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLConnectorTest {

    private SQLConnector sqlConnector;

    @BeforeEach
    void setUp() {
        // Khởi tạo instance trước mỗi test
        sqlConnector = SQLConnector.getInstance();
    }

    @Test
    @DisplayName("Test Singleton Instance")
    void testSingletonInstance() {
        SQLConnector instance1 = SQLConnector.getInstance();
        SQLConnector instance2 = SQLConnector.getInstance();

        // Kiểm tra cả hai instance là cùng một đối tượng
        assertSame(instance1, instance2, "SQLConnector không phải là singleton");
    }

    @Test
    @DisplayName("Test Successful Connection")
    void testSuccessfulConnection() {
        try {
            Connection connection = sqlConnector.getConnection();
            assertNotNull(connection, "Connection không được null");
            assertFalse(connection.isClosed(), "Connection không nên bị đóng");
        } catch (SQLException e) {
            fail("Kết nối thất bại: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Close Connection")
    void testCloseConnection() {
        try {
            // Mở kết nối
            Connection connection = sqlConnector.getConnection();
            assertNotNull(connection, "Kết nối không được null");

            // Đóng kết nối
            sqlConnector.closeDB();

            // Kiểm tra kết nối đã đóng
            assertTrue(connection.isClosed(), "Kết nối vẫn mở sau khi gọi closeDB");
        } catch (SQLException e) {
            fail("Test đóng kết nối thất bại: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Check Connection")
    void testCheckConnection() {
        boolean isConnected = sqlConnector.checkConnection();
        assertTrue(isConnected, "Phương thức checkConnection không hoạt động đúng");
    }

    @Test
    @DisplayName("Test Connection Fails with Wrong Credentials")
    void testConnectionFailsWithWrongCredentials() {
        SQLConnector invalidConnector = new SQLConnector() {
            @Override
            public Connection getConnection() throws SQLException {
                String wrongUrl = "jdbc:sqlserver://ADMIN\\NGOCMINH:1433;databaseName=QLThuphi;encrypt=true;trustServerCertificate=true;";
                String wrongUser = "sa";
                String wrongPassword = "123456789";
                return DriverManager.getConnection(wrongUrl, wrongUser, wrongPassword);
            }
        };

        // Kiểm tra xem ngoại lệ SQLException có được ném ra không khi kết nối thất bại
        assertThrows(SQLException.class, () -> {
            invalidConnector.getConnection();
        }, "Kết nối không nên thành công với thông tin sai");
    }

}
