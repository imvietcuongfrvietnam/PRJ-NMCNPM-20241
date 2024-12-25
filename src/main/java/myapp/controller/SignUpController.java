package myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import myapp.model.communicatedb.insert.UserAccountInsert;
import myapp.model.communicatedb.insert.UserInformationInsert;
import myapp.model.communicatedb.select.UserAccountSelect;
import myapp.model.manager.Switcher;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String defaultRole = "Quản trị viên";
    private String defaultGender = "Nam";
    private String defaultDateOfBirth = "01/01/1970";
    private String defaultIDcard = "0123456789";
    private String defaultHometown = "Hà Nội";
    private String defaultAddress = "Chung cư BlueMoon";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        logInButton.setOnAction(event -> {
            Switcher switcher = new Switcher();
            try {
                switcher.goLogInPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        signUpButton.setOnAction(event -> {
            lastName = lastNameText.getText();
            firstName = firstNameText.getText();
            email = emailText.getText();
            phone = phoneText.getText();
            username = usernameText.getText();
            password = passwordText.getText();
            if(lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Thiếu thông tin");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các trường.");
                alert.showAndWait();
            }
            else{
                UserAccountInsert userAccountInsert = new UserAccountInsert();
                userAccountInsert.insert(defaultRole, username, password, lastName + " " + firstName, defaultGender, defaultDateOfBirth, defaultIDcard, phone, email, defaultHometown, defaultAddress );
            }
        });
    }
}
