package myapp.model.dao.update;

import javafx.scene.control.Alert;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserAccountUpdate implements Updater<UserAccount> {
    @Override
    public void update(UserAccount entity) {
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getVaiTro());
            preparedStatement.setString(2, entity.getTenDangNhap());
            preparedStatement.setString(3, entity.getMatKhau());
            preparedStatement.setInt(4, entity.getMaTaiKhoan());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            showErrorAlert("Error Updating User Account", "An error occurred while updating the user account.", e.getMessage());
        }
    }

    public void updatePasswordByEmail(String email, String password) {
        String query = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE MaTaiKhoan = (" +
                "SELECT MaTaiKhoan FROM thongtinnguoidung WHERE Email = ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            showErrorAlert("Error Updating Password", "An error occurred while updating the password.", e.getMessage());
        }
    }

    public void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
