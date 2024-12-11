package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import myapp.dao.UserAccountDAO;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.LogManager;
import myapp.model.manager.Switcher;

import java.io.IOException;


/**
 * Controller lớp để xử lý các tương tác giữa giao diện người dùng và hệ thống trong màn hình đăng nhập.
 */
public class LogInController extends NavigationableController {
    @FXML
    private Label alertLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordText;

    @FXML
    private Button visibilityButton;

    @FXML
    private RadioButton saveSignInButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button registrationButton;

    private String password;
    private String username;

    /**
     * Phương thức khởi tạo các sự kiện và xử lý ban đầu cho các thành phần trong giao diện.
     */
    @Override
    public void initialize() {
        LogManager logManager = new LogManager();
        UserCredentials userCredentials = logManager.credentialsSaved();
        if(userCredentials == null){
            alertLabel.setText("Something have wrong when get the saved log in information.");
        }
        else {
            usernameText.setText(userCredentials.getUsername());
            passwordField.setText(userCredentials.getPassword());
        }
        alertLabel.setText("");
        signInButton.setOnAction(event -> {
            password = passwordField.getText();
            username = usernameText.getText();

            if (validateCredentials(username, password)) {
                try {
                    if(logManager.saveUserInfo(username)){
                        alertLabel.setText("Saved user information.");
                    }
                    else {
                        alertLabel.setText("Something went wrong.");
                    }
                    new Switcher().goHomePage(event, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alertLabel.setText("Log in information not correct!");
            }
        });

        registrationButton.setOnAction(event -> {
            try {
                new Switcher().goSignUpPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        visibilityButton.setOnAction(event -> passwordVisibility());
        saveSignInButton.setOnAction(event -> {
            if (saveSignInButton.isSelected()){
               if (logManager.savePassword(usernameText.getText(), passwordField.getText())){
                    alertLabel.setText("Saved log in information!.");
                }
                else{
                   alertLabel.setText("Something have wrong.");
               }
            }
        });
        forgotPasswordButton.setOnAction(event -> {
                    try {
                        new Switcher().goForgotPasswordPage(event,this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                );
    }

    /**
     * Phương thức chuyển đổi chế độ hiển thị mật khẩu giữa dạng ẩn và hiển thị.
     */
    private void passwordVisibility() {
        if (passwordField.isVisible()) {
            passwordField.setVisible(false);
            passwordText.setVisible(true);
            passwordText.setText(passwordField.getText());
        } else {
            passwordField.setVisible(true);
            passwordText.setVisible(false);
            passwordField.setText(passwordText.getText());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của thông tin đăng nhập (tên người dùng và mật khẩu).
     *
     * @param username Tên đăng nhập do người dùng nhập.
     * @param password Mật khẩu do người dùng nhập.
     * @return True nếu thông tin khớp với cơ sở dữ liệu, ngược lại trả về False.
     */
    private boolean validateCredentials(String username, String password) {
        return UserAccountDAO.selectByUsername(username).equals(password);
    }





}