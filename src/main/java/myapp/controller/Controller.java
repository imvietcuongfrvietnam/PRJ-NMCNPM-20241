package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button returnMainScreenButton;

    @FXML
    private void initialize() {
        userNameLabel.setText("Nguyen Van A");
        emailLabel.setText("nva@gmail.com");
        avatarImageView.setImage(new Image("/image/avatar.png"));
        
        // Gọi sự kiện cho nút Trở về
        returnMainScreenButton.setOnAction(event -> Switcher.switchTo("MainScreen.fxml"));
    }
}
