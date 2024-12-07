package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import myapp.model.communicatedb.select.UserInfoSelector;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.dao.select.PasswordSelector;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;

/**
 * Class dung de điều khiển các tương tác người duùng với hệ thống
 */

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

    /**
     * <p>
     *     Phương thức dùng để khoi tao cac su kien cho cac nut trong giao dien
     * </p>
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

    /**
     * <p>Phương thức dùng để hiển thị mật khẩu nếu chọn</p>
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
     * <p>Phương thức này dùng để kiểm tra tài khoản mật khẩu có khớp với trong hệ thống không</p>
     * @param username Tài khoản người dùng nhập
     * @param password Mật khẩu người dùng nhập
     * @return True nếu thông tin trùng khớp với thông tin trong hệ thống
     */
    private boolean validateCredentials(String username, String password) {
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }

    /**
     * <p>
     *     Phương thức này dùng để lưu lại thông tin đăng nhập nếu người dùng chọn lưu thông tin đăng nhập.
     *     <br> Thông tin được lưu trong file savepassword.json
     * </p>
     */
    private void savePassword() {
        if(saveSignInButton.isSelected()) {
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
     * <p>
     *     Phương thức này dùng để lấy thông tin đăng nhập đã được lưu trước đó trong hệ thống
     * </p>
     */
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

    /**
     * <p>
     *     Phương thức này lưu thông tin người dùng sau khi đăng nhập vào hệ thống, thông tin người dùng lưu trong userinfo.json
     * </p>

     * @param username Tài khoản người dùng
     *
     */
    private void saveUserInfo(String username) {
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();
        UserInfoSelector userInfoSelector = new UserInfoSelector();
        UserInformation userInfomation = userInfoSelector.select(username);
        userNode.put("soCMND", userInfomation.getSoCMND());
        userNode.put("Ten", userInfomation.getTen()); // Lưu ý: Mật khẩu không nên lưu trữ dạng plaintext, chỉ ví dụ
        userNode.put("sdt", userInfomation.getDienThoai());
        userNode.put("email", userInfomation.getEmail());

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
            alertLabel.setText("Đã lưu thông tin đăng nhập.");
        } catch (IOException e) {
            e.printStackTrace();
            alertLabel.setText("Lỗi khi lưu thông tin đăng nhập.");
        }
    }
}