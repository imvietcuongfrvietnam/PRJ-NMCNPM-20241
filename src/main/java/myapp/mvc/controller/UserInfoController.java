package myapp.mvc.controller;
import javafx.event.Event;
import myapp.mvc.model.manager.Switcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import myapp.mvc.model.entities.entitiessystem.UserInfo;

import java.io.File;
import java.io.IOException;

public class UserInfoController extends BaseController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField idField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField hometownField;

    @FXML
    public void initialize() {
        loadUserInfo();
    }

    /**
     * Load thông tin người dùng từ file JSON và hiển thị lên giao diện.
     */
    private void loadUserInfo() {
        File file = new File("src/main/resources/logs/user.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Đọc thông tin từ file JSON
            UserInfo userInfo = mapper.readValue(file, UserInfo.class);

            // Hiển thị thông tin lên các TextField
            if (userInfo != null) {
                nameField.setText(userInfo.getName());
                birthdayField.setText(userInfo.getBirthday());
                idField.setText(userInfo.getId());
                phoneField.setText(userInfo.getPhone());
                emailField.setText(userInfo.getEmail());
                hometownField.setText(userInfo.getHometown());
            } else {
                System.out.println("Thông tin người dùng không có.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi đọc file user.json: " + e.getMessage());
        }
    }

    /**
     * Quay lại giao diện Setting.
     */
    @FXML
    private void handleBackAction(Event event) {  // Thay MouseEvent bằng Event
        try {
            new Switcher().goHomePage(this, event);  // Chuyển event vào, không cần sửa `this`
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi chuyển trang: " + e.getMessage());
        }
    }


}