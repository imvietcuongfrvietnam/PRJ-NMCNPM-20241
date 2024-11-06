package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import myapp.model.communicatedb.select.PasswordSelector;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;

public class LogInController extends BaseController{

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
        this.showSaved();
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
        saveSignInButton.setOnAction(event -> {
            this.savePassword();
        });

    }

    private boolean validateCredentials(String username, String password) {
        PasswordSelector passwordSelector = new PasswordSelector();
        return passwordSelector.select(username).equals(password);
    }
    private void savePassword() {
        if (saveSignInButton.isSelected()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userNode = mapper.createObjectNode();

            userNode.put("username", usernameField.getText());
            userNode.put("password", passwordField.getText()); // Lưu ý: Mật khẩu không nên lưu trữ dạng plaintext, chỉ ví dụ

            try {
                // Ghi vào file savepassword.json
                File file = new File("src/main/resources/logs/savepassword.json");
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
                alertField.setText("Đã lưu thông tin đăng nhập.");
            } catch (IOException e) {
                e.printStackTrace();
                alertField.setText("Lỗi khi lưu thông tin đăng nhập.");
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
            usernameField.setText(credentials.getUsername());
            passwordField.setText(credentials.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file savepassword.json.");
        }

    }
}
