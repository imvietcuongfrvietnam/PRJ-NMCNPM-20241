package model.connectdb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector implements IConnectorDB {
    private static SQLConnector sqlconnector = null;
    private Connection connection = null;
    // Sử dụng Windows Authentication với integratedSecurity=true
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=QLChungCu;integratedSecurity=true";

    private SQLConnector() {
    }

    // Singleton pattern: Đảm bảo chỉ có một đối tượng SQLConnector duy nhất
    public static SQLConnector getInstance() {
        if (sqlconnector == null) {
            sqlconnector = new SQLConnector();
        }
        return sqlconnector;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Không cần userName và password khi sử dụng Windows Authentication
                connection = DriverManager.getConnection(url);
                System.out.println("Kết nối thành công!");
            } catch (SQLException e) {
                System.err.println("Kết nối thất bại: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    @Override
    public void closeDB() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Đóng kết nối thành công!");
            connection = null;
        }
    }
}
