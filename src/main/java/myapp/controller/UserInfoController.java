package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

public class UserInfoController {

    @FXML
    private ImageView avatarImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Button returnButton;

    @FXML
    public void initialize() {
        // Cài đặt hành động cho nút Trở Về
        returnButton.setOnAction(this::handleReturnButton);
    }

    private void handleReturnButton(ActionEvent event) {
        System.out.println("Trở về màn hình chính!");
        // Thêm logic chuyển cảnh nếu cần
    }
}
