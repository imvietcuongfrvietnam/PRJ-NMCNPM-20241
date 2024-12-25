package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentSelect {
    // Phương thức lấy thông tin mã căn hộ theo MaHoGiaDinh
    public static String getApartmentIDByHouseHoldID(String houseHoldID) {
        String apartmentID = null;
        String query = "SELECT MaCanHo FROM canho WHERE MaHoGiaDinh = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartmentID;
    }
}
