package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
                TaiKhoanNguoiDungInsert taiKhoanNguoiDungInsert = new TaiKhoanNguoiDungInsert();
                LocalDateTime now = LocalDateTime.now();
                taiKhoanNguoiDungInsert.insert(defaultRole, userName, password, now);
                ThongTinNguoiDungInsert thongTinNguoiDungInsert = new ThongTinNguoiDungInsert();
            thongTinNguoiDungInsert.insert(lastName + " " + firstName, defaultCMND, defaultNgaySinh, email, defaultQueQuan, phone, new MaTaiKhoanSelector().select());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Dang ki thanh cong");
                alert.setHeaderText(null);
                alert.setContentText("Ban da dang ki thanh cong, se chuyen toi trang dang nhap");
                alert.showAndWait();}
        });
    }

}
