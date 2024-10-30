package myapp.model.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PhongInsert {
    public void insert(String ID, int Tang, int DienTich, String TinhTrang, String ThongTinBoSung) {
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO phong (ID, Tang, DienTich, TinhTrang, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ID);
            preparedStatement.setInt(2, Tang);
            preparedStatement.setInt(3, DienTich);
            preparedStatement.setString(4, TinhTrang);
            preparedStatement.setString(5, ThongTinBoSung);

            preparedStatement.executeUpdate(); // Execute the insert statement
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
        } finally {
            // Close resources to avoid memory leaks
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
