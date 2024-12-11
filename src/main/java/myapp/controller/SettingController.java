package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import myapp.dao.UserInformationDAO;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.LogManager;
import myapp.model.manager.Switcher;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class SettingController extends NavigableController{
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
    private UserInformation userInformation;
    private UserCredentials userCredentials;

    @Override
    public void initialize() {
        super.initialize();
        setEditable(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        editPhotoButton.setVisible(false);
        editProfileButton.setOnAction(e -> editProfile());
        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> cancelEdit());
        editPhotoButton.setOnAction(e -> editPhoto());
        LogManager logManager = new LogManager();
        userInformation = logManager.readUserInfo();
        userCredentials = logManager.readUserCredentials();
        fillUserInfo();
    }
    private void fillUserInfo() {
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
        birthdayText.setValue(LocalDate.parse(userInformation.getNgaySinh().toLocaleString()));
        usernameText.setText(userCredentials.getUsername());
        passwordField.setText(userCredentials.getPassword());
//        // Điền thông tin ảnh hồ sơ nếu có (ví dụ: ảnh được lưu trữ dưới dạng URI)
//        // Giả sử bạn có trường photoProfile là Rectangle để hiển thị ảnh hồ sơ
//        String photoUrl = "src/main/resources/images/profile_" + userInformation.getSoCMND() + ".png";  // Ví dụ tên ảnh
//        File photoFile = new File(photoUrl);
//        if (photoFile.exists()) {
//            Image image = new Image(photoFile.toURI().toString());
//            photoProfile.setFill(new ImagePattern(image));
//        }
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
        // Lấy giá trị từ các trường
        String name = nameText.getText().trim();
        String id = idText.getText().trim();
        String phone = phoneText.getText().trim();
        String email = emailText.getText().trim();
        String hometown = hometownText.getText().trim();
        String address = addressText.getText().trim();
        String username = usernameText.getText().trim();
        String password = passwordText.getText().trim();
        LocalDate birthday = birthdayText.getValue();

        // Kiểm tra nếu có trường nào để trống
        if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                hometown.isEmpty() || address.isEmpty() || username.isEmpty() ||
                password.isEmpty() || birthday == null || (!maleButton.isSelected() && !femaleButton.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra định dạng email (nếu cần)
        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }

        // Đảm bảo trường mật khẩu không quá ngắn (ví dụ: ít nhất 6 ký tự)
        if (password.length() < 6) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Weak Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must be at least 6 characters long.");
            alert.showAndWait();
            return;
        }

        // Lưu thông tin sau khi kiểm tra thành công
        passwordField.setText(passwordText.getText());
        setEditable(false);

        new Thread(() -> {
            LogManager logManager = new LogManager();
            logManager.savePassword(username, password);
            logManager.saveUserInfo(username);

            userInformation.setTen(name);
            userInformation.setSoCMND(id);
            userInformation.setDienThoai(phone);
            userInformation.setEmail(email);
            userInformation.setQueQuan(hometown);
            userInformation.setDiaChi(address);
            userInformation.setNgaySinh(Date.valueOf(birthday.toString()));
            userInformation.setGioiTinh(maleButton.isSelected() ? "Male" : "Female");

            UserInformationDAO.updateUserInformation(userInformation, userCredentials);

            // Hiển thị thông báo thành công
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Update Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Your profile has been updated successfully!");
            successAlert.showAndWait();
        }).start();

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
