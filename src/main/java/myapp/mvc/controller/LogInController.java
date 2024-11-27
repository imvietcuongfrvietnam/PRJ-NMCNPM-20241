package myapp.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import myapp.mvc.model.dao.select.PasswordSelector;
import myapp.mvc.model.dao.select.UserInfoSelector;
import myapp.mvc.model.entities.entitiessystem.UserCredentials;
import myapp.mvc.model.entities.entitiessystem.UserInfo;
import myapp.mvc.model.manager.Switcher;

import java.io.File;
import java.io.IOException;

public class LogInController extends BaseController {
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

    @Override
    public void initialize() {
        this.showSaved();
        alertLabel.setText("");

        signInButton.setOnAction(event -> handleSignIn(event));
        registrationButton.setOnAction(event -> handleRegistration(event));
        visibilityButton.setOnAction(event -> passwordVisibility());
        saveSignInButton.setOnAction(event -> savePassword());
    }

    private void handleSignIn(ActionEvent event) {
        password = passwordField.getText();
        username = usernameText.getText();

        if (validateCredentials(username, password)) {
            try {
                saveUserInfo(username);
                new Switcher().goHomePage(this, event);
            } catch (IOException e) {
                e.printStackTrace();
                alertLabel.setText("Lỗi khi chuyển sang trang chủ.");
            }
        } else {
            alertLabel.setText("Tài khoản hoặc mật khẩu không hợp lệ");
        }
    }

    private void handleRegistration(javafx.event.ActionEvent event) {
        try {
            new Switcher().goSignUpPage(this, event);
        } catch (IOException e) {
            e.printStackTrace();
            alertLabel.setText("Lỗi khi chuyển sang trang đăng ký.");
        }
    }

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

    private boolean validateCredentials(String username, String password) {
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }

    private void savePassword() {
        if (saveSignInButton.isSelected()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userNode = mapper.createObjectNode();

            userNode.put("username", usernameText.getText());
            userNode.put("password", passwordField.getText());

            try {
                File file = new File("src/main/resources/logs/savepassword.json");
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
                alertLabel.setText("Đã lưu thông tin đăng nhập.");
            } catch (IOException e) {
                e.printStackTrace();
                alertLabel.setText("Lỗi khi lưu thông tin đăng nhập.");
            }
        }
    }

    private void showSaved() {
        File file = new File("src/main/resources/logs/savepassword.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserCredentials credentials = mapper.readValue(file, UserCredentials.class);
            usernameText.setText(credentials.getUsername());
            passwordField.setText(credentials.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file savepassword.json.");
        }
    }

    private void saveUserInfo(String tenDangNhap) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/logs/user.json");
        UserInfoSelector userInfoSelector = new UserInfoSelector();
        UserInfo userInfo = userInfoSelector.select(tenDangNhap);

        if (userInfo != null) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("name", userInfo.getName());
            objectNode.put("birthday", userInfo.getBirthday());
            objectNode.put("id", userInfo.getId());
            objectNode.put("phone", userInfo.getPhone());
            objectNode.put("email", userInfo.getEmail());
            objectNode.put("hometown", userInfo.getHometown());

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, objectNode);
            } catch (IOException e) {
                e.printStackTrace();
                alertLabel.setText("Lỗi khi lưu thông tin người dùng.");
            }
        }
    }
}
