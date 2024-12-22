package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillUpdate {
    public void update(Bill bill) {
                String query = "UPDATE quanlyhoadon SET TenNhaCungCap = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bill.getSupplierName());
            preparedStatement.setString(2, bill.getHouseHoldID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
