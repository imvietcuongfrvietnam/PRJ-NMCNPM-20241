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
import java.time.LocalDateTime;
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
    private String userName;
    private String password;
    private String defaultCMND = "";
    private String defaultQueQuan = "";
    private String defaultNgaySinh = "01/01/1970";
    private String defaultRole = "admin";

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
            // Lấy giá trị của từng trường
            lastName = lastNameText.getText();
            firstName = firstNameText.getText();
            email = emailText.getText();
            phone = phoneText.getText();
            userName = usernameText.getText();
            password = passwordText.getText();
            if(lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Thiếu thông tin");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các trường.");
                alert.showAndWait();
            }
            else{
                UserAccountInsert taiKhoanNguoiDungInsert = new UserAccountInsert();
                LocalDateTime now = LocalDateTime.now();
                taiKhoanNguoiDungInsert.insert(defaultRole, userName, password, now);
                UserInformationInsert thongTinNguoiDungInsert = new UserInformationInsert();
            thongTinNguoiDungInsert.insert(lastName + " " + firstName, defaultCMND, defaultNgaySinh, email, defaultQueQuan, phone, new UserAccountSelect().select());}
        });
    }

}
