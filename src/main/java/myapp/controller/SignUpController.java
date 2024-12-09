package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import myapp.model.dao.insert.UserAccountInsert;
import myapp.model.dao.insert.UserInformationInsert;
import myapp.model.dao.select.MaTaiKhoanSelector;
import myapp.model.manager.Switcher;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDateTime;

public class SignUpController extends BaseController {
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
    private String defaultNgaySinh = "1970-01-01";
    private String defaultRole = "admin";

    @Override
    public void initialize() {
        logInButton.setOnAction(event -> {
            Switcher switcher = new Switcher();
            try {
                switcher.goLogInPage(event, this);
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

            // Kiểm tra nếu có trường thông tin trống
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Thiếu thông tin");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các trường.");
                alert.showAndWait();
            } else {
                // Kiểm tra trùng lặp tên tài khoản
                MaTaiKhoanSelector selector = new MaTaiKhoanSelector();
                if (selector.isUserNameExist(userName)) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Tài khoản đã tồn tại");
                    alert.setHeaderText(null);
                    alert.setContentText("Tên tài khoản này đã tồn tại. Vui lòng chọn tên khác.");
                    alert.showAndWait();
                    return;
                }

                // Chèn tài khoản người dùng vào cơ sở dữ liệu
                UserAccountInsert taiKhoanNguoiDungInsert = new UserAccountInsert();
                LocalDateTime now = LocalDateTime.now();
                taiKhoanNguoiDungInsert.insert(defaultRole, userName, password, now);

                // Chèn thông tin người dùng vào cơ sở dữ liệu
                UserInformationInsert thongTinNguoiDungInsert = new UserInformationInsert();
                thongTinNguoiDungInsert.insert(
                        lastName + " " + firstName,
                        defaultCMND,
                        defaultNgaySinh,
                        email,
                        defaultQueQuan,
                        phone,
                        selector.select()
                );

                // Thông báo thành công
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Đăng ký thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Chúc mừng! Bạn đã đăng ký tài khoản thành công.");
                successAlert.showAndWait();
            }
        });
    }
}
