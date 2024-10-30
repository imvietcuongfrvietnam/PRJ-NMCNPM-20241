package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

public class UserInfoController {

    @FXML
    private ImageView imgView;
    @FXML
    private Label lblTen;
    @FXML
    private Label lblSoCMND;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblQueQuan;
    @FXML
    private Label lblDienThoai;
    @FXML
    private Button btnReturn;

    @FXML
    public void initialize() {
        // Thiết lập thông tin mẫu
        lblTen.setText("Nguyen Van A");
        lblSoCMND.setText("123456789");
        lblEmail.setText("nva@gmail.com");
        lblQueQuan.setText("Hanoi");
        lblDienThoai.setText("0123456789");
        imgView.setImage(new Image("/image/avatar.png"));

        // Gọi sự kiện cho nút Trở về
        btnReturn.setOnAction(this::handleReturnButton);
    }

    private void handleReturnButton(ActionEvent event) {
        Switcher.switchTo("MainScreen.fxml");
    }
}
