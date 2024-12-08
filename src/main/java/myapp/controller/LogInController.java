package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import myapp.model.dao.select.*;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.dao.select.PasswordSelector;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;

/**
 * Controller lớp để xử lý các tương tác giữa giao diện người dùng và hệ thống trong màn hình đăng nhập.
 */
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

    /**
     * Phương thức khởi tạo các sự kiện và xử lý ban đầu cho các thành phần trong giao diện.
     */
    @Override
    public void initialize() {
        this.showSaved();
        alertLabel.setText("");
        signInButton.setOnAction(event -> {
            password = passwordField.getText();
            username = usernameText.getText();

            if (validateCredentials(username, password)) {
                try {
                    saveUserInfo(username);
                    new Switcher().goHomePage(event, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alertLabel.setText("Tài khoản hoặc mật khẩu không hợp lệ");
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
        saveSignInButton.setOnAction(event -> savePassword());
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
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }

    /**
     * Lưu thông tin đăng nhập của người dùng (nếu được chọn) vào tệp JSON.
     * Thông tin được lưu trữ trong tệp `savepassword.json`.
     */
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

    /**
     * Hiển thị thông tin đăng nhập được lưu trước đó (nếu có) từ tệp JSON.
     */
    private void showSaved() {
        File file = new File("src/main/resources/logs/savepassword.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserCredentials credentials = mapper.readValue(file, UserCredentials.class);
            usernameText.setText(credentials.getUsername());
            passwordField.setText(credentials.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            alertLabel.setText("Lỗi khi lấy lại mât khẩu đã lưu.");
        }
    }

    /**
     * Lưu thông tin người dùng đăng nhập vào hệ thống dưới dạng JSON.
     * Thông tin này bao gồm các trường cơ bản của người dùng như số CMND, tên, số điện thoại và email.
     *
     * @param username Tên đăng nhập của người dùng.
     */
    private void saveUserInfo(String username) {
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();
        UserInfoSelector userInfoSelector = new UserInfoSelector();
        UserInformation userInfomation = userInfoSelector.select(username);
        userNode.put("soCMND", userInfomation.getSoCMND());
        userNode.put("Ten", userInfomation.getTen());
        userNode.put("sdt", userInfomation.getDienThoai());
        userNode.put("email", userInfomation.getEmail());

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
            alertLabel.setText("Đã lưu thông tin người dùng.");
        } catch (IOException e) {
            e.printStackTrace();
            alertLabel.setText("Lỗi khi lưu thông tin người dùng.");
        }
    }
}