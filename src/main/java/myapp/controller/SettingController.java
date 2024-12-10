package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SettingController {
    @FXML
    private Button editProfileButton;
    @FXML
    private TextField nameText;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton maleButton;
    @FXML
    private RadioButton femaleButton;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private TextField idText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField hometownText;
    @FXML
    private TextField addressText;
    @FXML
    private Circle photoProfile;
    @FXML
    private Button editPhotoButton;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private String passwordBeforeEdit;
    private Image profileImageBeforeEdit;

    @FXML
    public void initialize() {
        nameText.setEditable(false);
        idText.setEditable(false);
        phoneText.setEditable(false);
        emailText.setEditable(false);
        hometownText.setEditable(false);
        addressText.setEditable(false);
        usernameText.setEditable(false);
        passwordText.setEditable(false);
        birthdayText.setMouseTransparent(true);
        maleButton.setMouseTransparent(true);
        femaleButton.setMouseTransparent(true);

        editProfileButton.setOnAction(e -> editProfile());
        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> cancelEdit());
        editPhotoButton.setOnAction(e -> editPhoto());
    }

    private void editProfile() {
        passwordBeforeEdit = passwordField.getText();

        // Đặt các trường sang chế độ chỉnh sửa
        nameText.setEditable(true);
        idText.setEditable(true);
        phoneText.setEditable(true);
        emailText.setEditable(true);
        hometownText.setEditable(true);
        addressText.setEditable(true);
        usernameText.setEditable(true);
        passwordField.setEditable(true);
        passwordText.setEditable(true);
        birthdayText.setMouseTransparent(false);
        maleButton.setMouseTransparent(false);
        femaleButton.setMouseTransparent(false);

        passwordField.setVisible(false);
        passwordText.setVisible(true);
        passwordText.setText(passwordField.getText());

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        editPhotoButton.setVisible(true);

        editProfileButton.setVisible(false);
    }

    private void editPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Thay đổi ảnh hồ sơ");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(editPhotoButton.getScene().getWindow());

        if (selectedFile != null) {
            if (photoProfile.getFill() instanceof ImagePattern) {
                if (profileImageBeforeEdit == null) {
                    profileImageBeforeEdit = ((ImagePattern) photoProfile.getFill()).getImage();
                }
            }

            Image originalImage = new Image(selectedFile.toURI().toString());
            double width = originalImage.getWidth();
            double height = originalImage.getHeight();
            double size = Math.min(width, height);

            WritableImage croppedImage = new WritableImage(
                    originalImage.getPixelReader(),
                    (int)((width - size) / 2),
                    (int)((height - size) / 2),
                    (int)size,
                    (int)size
            );

            photoProfile.setFill(new ImagePattern(croppedImage));
        }
    }


    @FXML
    private void saveChanges() {
        // Đặt các trường không thể chỉnh sửa
        nameText.setEditable(false);
        idText.setEditable(false);
        phoneText.setEditable(false);
        emailText.setEditable(false);
        hometownText.setEditable(false);
        addressText.setEditable(false);
        usernameText.setEditable(false);
        passwordField.setEditable(false);
        passwordText.setEditable(false);
        birthdayText.setMouseTransparent(true);
        maleButton.setMouseTransparent(true);
        femaleButton.setMouseTransparent(true);

        // Hiển thị mật khẩu bằng dạng Field
        passwordField.setText(passwordText.getText());
        passwordField.setVisible(true);
        passwordText.setVisible(false);

        // Cập nhật trạng thái các nút
        editProfileButton.setVisible(true);
        editPhotoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);

        // Kiểm tra các trường dữ liệu
        if (nameText.getText().isEmpty() || idText.getText().isEmpty() || phoneText.getText().isEmpty() ||
                emailText.getText().isEmpty() || hometownText.getText().isEmpty() || addressText.getText().isEmpty() ||
                usernameText.getText().isEmpty() || passwordText.getText().isEmpty() ||
                birthdayText.getValue() == null || (!maleButton.isSelected() && !femaleButton.isSelected())) {

            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin vào các trường thông tin!");
            return;
        }
    }

    @FXML
    private void cancelEdit() {
        nameText.setEditable(false);
        idText.setEditable(false);
        phoneText.setEditable(false);
        emailText.setEditable(false);
        hometownText.setEditable(false);
        addressText.setEditable(false);
        usernameText.setEditable(false);
        passwordField.setEditable(false);
        passwordText.setEditable(false);
        birthdayText.setMouseTransparent(true);
        maleButton.setMouseTransparent(true);
        femaleButton.setMouseTransparent(true);
        editPhotoButton.setVisible(false);

        passwordField.setText(passwordBeforeEdit);
        passwordField.setVisible(true);
        passwordText.setVisible(false);

        if (profileImageBeforeEdit != null) {
            photoProfile.setFill(new ImagePattern(profileImageBeforeEdit));
        }

        editProfileButton.setVisible(true);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
