package myapp.model.connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector implements IConnectorDB {
    private static SQLConnector sqlconnector = null;
    private Connection connection = null;

    // Thêm các tham số cấu hình tên người dùng và mật khẩu SQL Server
    private final String url = "jdbc:sqlserver://LAPTOP-6EEOSOCP\\SQLEXPRESS:1433;databaseName=QLThuphi;encrypt=true;trustServerCertificate=true;";
    private final String user = "sa"; // Tên người dùng
    private final String password = "123456789"; // Mật khẩu

    public SQLConnector() {
    }

    public static SQLConnector getInstance() {
        if (sqlconnector == null) {
            sqlconnector = new SQLConnector();
        }
        return sqlconnector;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Cập nhật chuỗi kết nối để sử dụng tài khoản SQL Server với tên người dùng và mật khẩu
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    @Override
    public void closeDB() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    // Hàm kiểm tra kết nối
    public boolean checkConnection() {
        try (Connection testConnection = DriverManager.getConnection(url, user, password)) {
            return testConnection != null && !testConnection.isClosed();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại: " + e.getMessage());
            return false;
        }
    }
}
