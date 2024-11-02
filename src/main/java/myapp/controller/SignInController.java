package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import myapp.model.manager.Switcher;
public class SignInController {

    @FXML
    private Button signInButton;

    @FXML
    private TextField usernameField;  // Trường nhập tên người dùng

    @FXML
    private PasswordField passwordField; // Trường nhập mật khẩu

    @FXML
    private Label errorLabel; // Nhãn để hiển thị thông báo lỗi

    private final Switcher switcher = new Switcher(); // Để chuyển đổi cảnh

    @FXML
    private void initialize() {
        // Xử lý logic đăng nhập tại đây nếu cần
    }

    @FXML
    private void handleSignInButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateCredentials(username, password)) {
            // Nếu thông tin hợp lệ, chuyển đến HomePage
            switcher.goHomePage();
        } else {
            // Nếu thông tin không hợp lệ, hiển thị thông báo lỗi
            errorLabel.setText("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }

    private boolean validateCredentials(String username, String password) {
        // Kiểm tra thông tin đăng nhập
        // Bạn có thể thay thế logic này để kiểm tra từ cơ sở dữ liệu
        return "user".equals(username) && "pass".equals(password);
    }
}
