package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import myapp.model.communicatedb.select.PasswordSelector;
import myapp.model.manager.Switcher;

import java.io.IOException;

public class LogInController extends BaseController implements Runnable{

    @FXML
    private Button signInButton;
    @FXML
    private RadioButton saveSignInButton;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label alertField;

    @FXML
    private Button registrationButton;

    @FXML
    private ImageView viewPassword;

    private String password;
    private String username;

    @Override
    public void initialize() {
        alertField.setText("");
        signInButton.setOnAction(event -> {
            password = passwordField.getText();
            username = usernameField.getText();

            if (validateCredentials(username, password)) {
                try {
                    new Switcher().goHomePage(this, event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alertField.setText("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });

        registrationButton.setOnAction(event -> {
            try {
                new Switcher().goSignUpPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        viewPassword.setOnMousePressed(event -> {
            // Thay đổi để hiển thị mật khẩu dưới dạng TextField
            passwordField.setStyle("-fx-opacity: 1;");  // Loại bỏ hiệu ứng che giấu mật khẩu
            passwordField.setText(passwordField.getText());  // Đảm bảo mật khẩu được hiển thị rõ ràng
            passwordField.setPromptText("");  // Nếu có PromptText thì đặt lại
        });

        viewPassword.setOnMouseReleased(event -> {
            // Khôi phục lại mật khẩu dưới dạng PasswordField khi thả chuột
            passwordField.setStyle("-fx-opacity: 0;");  // Đặt lại chế độ ẩn mật khẩu
            passwordField.setPromptText("Password");  // Đặt lại PromptText nếu cần
        });


    }

    private boolean validateCredentials(String username, String password) {
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }

    @Override
    public void run() {
    }
}
