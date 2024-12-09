package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import myapp.model.entities.entitiesdb.UserInformation;

import java.io.File;
import java.time.LocalDate;

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
    private Rectangle photoProfile;
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

    @FXML
    public void initialize() {
        setEditable(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        editPhotoButton.setVisible(false);

        editProfileButton.setOnAction(e -> editProfile());
        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> cancelEdit());
        editPhotoButton.setOnAction(e -> editPhoto());
    }
    private void fillUserInfo(UserInformation userInformation) {
        // Điền các trường thông tin cá nhân vào các TextField
        nameText.setText(userInformation.getTen());
        idText.setText(userInformation.getSoCMND());
        phoneText.setText(userInformation.getDienThoai());
        emailText.setText(userInformation.getEmail());
        hometownText.setText(userInformation.getQueQuan());
        addressText.setText(userInformation.getDiaChi());

        // Điền thông tin giới tính
        if ("Male".equals(userInformation.getGioiTinh())) {
            maleButton.setSelected(true);
        } else if ("Female".equals(userInformation.getGioiTinh())) {
            femaleButton.setSelected(true);
        }

        // Điền ngày sinh
        birthdayText.setValue(LocalDate.parse(userInformation.getNgaySinh().toLocaleString()));

        // Điền thông tin ảnh hồ sơ nếu có (ví dụ: ảnh được lưu trữ dưới dạng URI)
        // Giả sử bạn có trường photoProfile là Rectangle để hiển thị ảnh hồ sơ
        String photoUrl = "src/main/resources/images/profile_" + userInformation.getSoCMND() + ".png";  // Ví dụ tên ảnh
        File photoFile = new File(photoUrl);
        if (photoFile.exists()) {
            Image image = new Image(photoFile.toURI().toString());
            photoProfile.setFill(new ImagePattern(image));
        }
    }


    private void setEditable(boolean isEditable) {
        nameText.setEditable(isEditable);
        idText.setEditable(isEditable);
        phoneText.setEditable(isEditable);
        emailText.setEditable(isEditable);
        hometownText.setEditable(isEditable);
        addressText.setEditable(isEditable);
        usernameText.setEditable(isEditable);
        passwordField.setDisable(!isEditable);
        passwordText.setEditable(isEditable);
        birthdayText.setDisable(!isEditable);
        maleButton.setDisable(!isEditable);
        femaleButton.setDisable(!isEditable);
        editPhotoButton.setDisable(!isEditable);
    }

    private void editProfile() {
        passwordBeforeEdit = passwordField.getText();
        setEditable(true);

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
            Image image = new Image(selectedFile.toURI().toString());
            photoProfile.setFill(new ImagePattern(image));
        }
    }

    @FXML
    private void saveChanges() {
        passwordField.setText(passwordText.getText());
        setEditable(false);

        passwordField.setVisible(true);
        passwordText.setVisible(false);

        editProfileButton.setVisible(true);
        editPhotoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);

    }

    @FXML
    private void cancelEdit() {
        passwordField.setText(passwordBeforeEdit);
        setEditable(false);

        passwordField.setVisible(true);
        passwordText.setVisible(false);

        editProfileButton.setVisible(true);
        editPhotoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);

    }
}
