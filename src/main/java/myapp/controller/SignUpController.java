package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateTimeStringConverter;
import myapp.model.communicatedb.insert.TaiKhoanNguoiDungInsert;
import myapp.model.communicatedb.insert.ThongTinNguoiDungInsert;
import myapp.model.communicatedb.select.MaTaiKhoanSelector;
import myapp.model.manager.Switcher;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDateTime;

public class SignUpController extends BaseController{
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
    String defaultCMND = "";
    String defaultQueQuan = "";
    String defaultNgaySinh = "01/01/1970";
    String defaultRole = "admin";
    @Override
    public void initialize(){
        logInButton.setOnAction(event -> {
            Switcher switcher = new Switcher();
            try {
                switcher.goLogInPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                TaiKhoanNguoiDungInsert taiKhoanNguoiDungInsert = new TaiKhoanNguoiDungInsert();
                LocalDateTime now = LocalDateTime.now();
                taiKhoanNguoiDungInsert.insert(defaultRole, userName, password, now);
                ThongTinNguoiDungInsert thongTinNguoiDungInsert = new ThongTinNguoiDungInsert();
            thongTinNguoiDungInsert.insert(lastName + " " + firstName, defaultCMND, defaultNgaySinh, email, defaultQueQuan, phone, new MaTaiKhoanSelector().select());}

        });
    }

}
