package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillUpdate implements Updater<Bill> {
    @Override
    public void update(Bill entity) {
                String query = "UPDATE quanlyhoadon SET TenNhaCungCap = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getSupplierName());
            preparedStatement.setString(2, entity.getHouseHoldID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
