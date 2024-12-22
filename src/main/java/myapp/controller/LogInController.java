package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import myapp.model.communicatedb.select.UserAccountSelect;
import myapp.model.entities.entitiesdb.UserAccount;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
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
    private Button registrationButton, signInButton, forgotPasswordButton;
    private String password;
    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showSaved();
        alertLabel.setText("");
        signInButton.setOnAction(event -> {
            password = passwordField.getText();
            username = usernameText.getText();

            if (validateCredentials(username, password)) {
                try {
                    new Switcher().goHomePage(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        registrationButton.setOnAction(event -> {
            try {
                new Switcher().goSignUpPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        forgotPasswordButton.setOnAction(event -> {
            try {
                new Switcher().goForgotPasswordPage(event);
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
        // Lấy thông tin người dùng từ cơ sở dữ liệu
        UserAccount user = new UserAccountSelect().getUserByUsername(username);

        if (user == null) {
            // Nếu không tìm thấy tài khoản trong cơ sở dữ liệu
            alertLabel.setText("Tài khoản không tồn tại.");
            return false;
        } else {
            // Nếu tìm thấy tài khoản, kiểm tra mật khẩu
            if (user.getPassword().equals(password)) {
                // Nếu mật khẩu đúng, lưu thông tin người dùng vào tệp JSON
                ObjectMapper mapper = new ObjectMapper();
                File file = new File("src/main/resources/logs/userInformation.json");
                try {
                    // Chuyển đổi đối tượng UserAccount thành JSON và lưu vào tệp
                    mapper.writeValue(file, user);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Không thể lưu thông tin người dùng vào tệp.");
                }
                return true;
            } else {
                // Nếu mật khẩu không đúng
                alertLabel.setText("Mật khẩu không chính xác. Hãy thử lại.");
                return false;
            }
        }
    }

    private void savePassword() {
        if(saveSignInButton.isSelected()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userNode = mapper.createObjectNode();

            userNode.put("username", usernameText.getText());
            userNode.put("password", passwordField.getText()); // Lưu ý: Mật khẩu không nên lưu trữ dạng plaintext, chỉ ví dụ

            try {
                File file = new File("src/main/resources/logs/userInformation.json");
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
                alertLabel.setText("Đã lưu thông tin đăng nhập.");
            } catch (IOException e) {
                e.printStackTrace();
                alertLabel.setText("Lỗi khi lưu thông tin đăng nhập.");
            }

        }
    }
    private void showSaved(){
        File file = new File("src/main/resources/logs/userInformation.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserAccount userAccount = mapper.readValue(file, UserAccount.class);
            usernameText.setText(userAccount.getUsername());
            passwordField.setText(userAccount.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file userInformation.json.");
        }
    }
}
