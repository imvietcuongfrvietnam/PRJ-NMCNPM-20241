package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import myapp.model.communicatedb.select.PasswordSelector;
import myapp.model.communicatedb.select.UserInfoSelector;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.entities.entitiessystem.UserInfo;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;

public class LogInController extends BaseController{
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
        signInButton.setOnAction(event -> {
            password = passwordField.getText();
            username = usernameText.getText();

            if (validateCredentials(username, password)) {
                try {
                    saveUserInfo(username);

                    new Switcher().goHomePage(this, event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alertLabel.setText("Tài khoản hoặc mật khẩu không hợp lệ");
            }
        });

        registrationButton.setOnAction(event -> {
            try {
                new Switcher().goSignUpPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        visibilityButton.setOnAction(event -> passwordVisibility());
        saveSignInButton.setOnAction(event -> {
            this.savePassword();
        });

    }
    private void passwordVisibility() {
        // Kiểm tra nếu passwordField đang hiển thị
        if (passwordField.isVisible()) {
            // Ẩn passwordField và hiển thị passwordText
            passwordField.setVisible(false);
            passwordText.setVisible(true);
            // Đảm bảo nội dung của passwordText giống với passwordField
            passwordText.setText(passwordField.getText());
        } else {
            // Ẩn passwordText và hiển thị passwordField
            passwordField.setVisible(true);
            passwordText.setVisible(false);
            // Đảm bảo nội dung của passwordField giống với passwordText
            passwordField.setText(passwordText.getText());
        }
    }

    private boolean validateCredentials(String username, String password) {
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }
    private void savePassword() {
        if(saveSignInButton.isSelected()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userNode = mapper.createObjectNode();

            userNode.put("username", usernameText.getText());
            userNode.put("password", passwordField.getText()); // Lưu ý: Mật khẩu không nên lưu trữ dạng plaintext, chỉ ví dụ

            try {
                // Ghi vào file savepassword.json
                File file = new File("src/main/resources/logs/savepassword.json");
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
                alertLabel.setText("Đã lưu thông tin đăng nhập.");
            } catch (IOException e) {
                e.printStackTrace();
                alertLabel.setText("Lỗi khi lưu thông tin đăng nhập.");
            }

        }
    }
    private void showSaved(){
        File file = new File("src/main/resources/logs/savepassword.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Đọc nội dung file và ánh xạ vào đối tượng UserCredentials
            UserCredentials credentials = mapper.readValue(file, UserCredentials.class);
            // Hiển thị thông tin
            usernameText.setText(credentials.getUsername());
            passwordField.setText(credentials.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file savepassword.json.");
        }

    }
    private void saveUserInfo(String tenDangNhap){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/logs/user.json");
        ObjectMapper mappper = new ObjectMapper();
        UserInfoSelector userInfoSelector = new UserInfoSelector();
        UserInfo userInfo = userInfoSelector.select(tenDangNhap);
        if(userInfo != null){
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("name", userInfo.getName());
            objectNode.put("gender", userInfo.getGender());
            objectNode.put("birthday", userInfo.getBirthday());
            objectNode.put("id", userInfo.getId());
            objectNode.put("phone", userInfo.getPhone());
            objectNode.put("email", userInfo.getEmail());
            objectNode.put("hometown", userInfo.getHometown());
            objectNode.put("address", userInfo.getAddress());
        }
    }
}
