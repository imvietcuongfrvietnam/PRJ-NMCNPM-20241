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

/**
 * Lớp điều khiển cho màn hình đăng ký người dùng.
 * Chức năng bao gồm:
 * - Đăng ký tài khoản mới.
 * - Chuyển sang màn hình đăng nhập.
 */
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

    /**
     * Phương thức khởi tạo màn hình đăng ký.
     * Gắn các trình xử lý sự kiện cho các nút.
     */
    @Override
    public void initialize() {
        // Chuyển sang màn hình đăng nhập
        logInButton.setOnAction(event -> {
            Switcher switcher = new Switcher();
            try {
                switcher.goLogInPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút Sign Up
        signUpButton.setOnAction(event -> {
            lastName = lastNameText.getText();
            firstName = firstNameText.getText();
            email = emailText.getText();
            phone = phoneText.getText();
            userName = usernameText.getText();
            password = passwordText.getText();

            // Kiểm tra nếu có trường nào bị bỏ trống
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all the fields.");
                alert.showAndWait();
            } else {
                // Kiểm tra nếu tên tài khoản đã tồn tại
                MaTaiKhoanSelector selector = new MaTaiKhoanSelector();
                if (selector.isUserNameExist(userName)) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Username Already Exists");
                    alert.setHeaderText(null);
                    alert.setContentText("This username already exists. Please choose another one.");
                    alert.showAndWait();
                    return;
                }

                // Thêm tài khoản người dùng vào cơ sở dữ liệu
                UserAccountInsert userAccountInsert = new UserAccountInsert();
                LocalDateTime now = LocalDateTime.now();
                userAccountInsert.insert(defaultRole, userName, password, now);

                // Thêm thông tin người dùng vào cơ sở dữ liệu
                UserInformationInsert userInformationInsert = new UserInformationInsert();
                userInformationInsert.insert(
                        lastName + " " + firstName,
                        defaultCMND,
                        defaultNgaySinh,
                        email,
                        defaultQueQuan,
                        phone,
                        selector.select()
                );

                // Hiển thị thông báo thành công
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Registration Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Congratulations! You have successfully signed up.");
                successAlert.showAndWait();
            }
        });
    }
}
