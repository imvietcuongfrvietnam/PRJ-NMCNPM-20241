package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class UserInfoController {

    @FXML
    private ImageView imgView;          // Biến cho ImageView
    @FXML
    private Label lblTen;               // Biến cho Label họ tên
    @FXML
    private Label lblSoCMND;            // Biến cho Label số CMND
    @FXML
    private Label lblEmail;              // Biến cho Label email
    @FXML
    private Label lblQueQuan;            // Biến cho Label quê quán
    @FXML
    private Label lblDienThoai;         // Biến cho Label điện thoại
    @FXML
    private Button btnReturn;            // Biến cho Button

    @FXML
    public void initialize() {
        // Thiết lập hành động cho nút Trở Về
        btnReturn.setOnAction(this::handleReturnButton);
    }

    // Phương thức xử lý sự kiện khi nhấn nút Trở Về
    private void handleReturnButton(ActionEvent event) {
        System.out.println("Trở về màn hình chính!");
        // Thêm logic ở đây nếu cần để quay lại màn hình chính
    }
}
