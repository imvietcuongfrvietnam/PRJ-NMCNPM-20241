package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import myapp.model.manager.Switcher;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SignUpController {
    @FXML
    TextField lastNameField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneField;
    @FXML
    TextField userNameField;
    @FXML
    TextField passwordField;
    @FXML
    Button signUpButton;
    @FXML
    Button logInButton;
    String lastName;
    String firstName;
    String email;
    String phone;
    String userName;
    String password;

    public void initialize(){
        logInButton.setOnAction(event -> {
            Switcher switcher = new Switcher();
            switcher.goLogInPage();
        });

        signUpButton.setOnAction(event -> {
            // Lấy giá trị của từng trường
            lastName = lastNameField.getText();
            firstName = firstNameField.getText();
            email = emailField.getText();
            phone = phoneField.getText();
            userName = userNameField.getText();
            password = passwordField.getText();
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() ||
                    phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Thiếu thông tin");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các trường.");
                alert.showAndWait();
            }
            else{

            }
        });
    }

}
