package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FeeUpdate {
    public void update(String feeID, String amount) {
        String query = "UPDATE banggiakhoanphi SET DonGia = ? WHERE MaKhoanPhi = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, amount);
            preparedStatement.setString(2, feeID);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
