package myapp.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectorDB {
    public Connection getConnection() throws Exception;
    public void closeDB() throws Exception;
}
