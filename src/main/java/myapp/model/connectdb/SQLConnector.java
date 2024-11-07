package myapp.model.connectdb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLConnector implements IConnectorDB{
    private static SQLConnector sqlconnector = null;
    private Connection connection = null;
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=QLThuphi;encrypt=true;trustServerCertificate=true;";
    private final String userName = "root";
    private final String password = "";
    private SQLConnector() {
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
            connection = DriverManager.getConnection(url, userName, password);
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

}
