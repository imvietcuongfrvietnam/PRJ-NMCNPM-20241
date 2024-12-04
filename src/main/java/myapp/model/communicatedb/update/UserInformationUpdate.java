package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserInformationUpdate implements Updater<UserInformation> {
    @Override
    public void update(UserInformation entity) {
        String query = "UPDATE thongtinnguoidung SET Ten = ?, Email = ?, QueQuan = ?, DienThoai = ? WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTen());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getQueQuan());
            preparedStatement.setString(4, entity.getDienThoai());
            preparedStatement.setString(5, entity.getSoCMND());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
