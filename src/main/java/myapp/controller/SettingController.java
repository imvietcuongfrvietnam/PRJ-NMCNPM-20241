package myapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import myapp.dao.UserInformationDAO;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;
import myapp.model.manager.LogManager;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

public class SettingController extends NavigableController {

    @FXML private Button editProfileButton;
    @FXML private Button editPhotoButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @FXML private TextField nameText;
    @FXML private TextField idText;
    @FXML private TextField phoneText;
    @FXML private TextField emailText;
    @FXML private TextField hometownText;
    @FXML private TextField addressText;
    @FXML private TextField usernameText;
    @FXML private TextField passwordText;

    @FXML private PasswordField passwordField;
    @FXML private DatePicker birthdayText;

    @FXML private RadioButton maleButton;
    @FXML private RadioButton femaleButton;
    @FXML private ToggleGroup genderGroup;

    @FXML private Circle photoProfile;

    private String passwordBeforeEdit;
    private UserInformation userInformation;
    private UserCredentials userCredentials;

    @Override
    public void initialize() {
        super.initialize();
        setEditable(false);
        toggleEditButtons(false);

        LogManager logManager = new LogManager();
        userInformation = logManager.readUserInfo();
        userCredentials = logManager.readUserCredentials();
        fillUserInfo();

        editProfileButton.setOnAction(e -> editProfile());
        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> cancelEdit());
        editPhotoButton.setOnAction(e -> editPhoto());
    }

    private void fillUserInfo() {
        nameText.setText(userInformation.getTen());
        idText.setText(userInformation.getSoCMND());
        phoneText.setText(userInformation.getDienThoai());
        emailText.setText(userInformation.getEmail());
        hometownText.setText(userInformation.getQueQuan());
        addressText.setText(userInformation.getDiaChi());

        if ("Male".equals(userInformation.getGioiTinh())) {
            maleButton.setSelected(true);
        } else if ("Female".equals(userInformation.getGioiTinh())) {
            femaleButton.setSelected(true);
        }

        birthdayText.setValue(LocalDate.parse(userInformation.getNgaySinh().toString()));
        usernameText.setText(userCredentials.getUsername());
        passwordField.setText(userCredentials.getPassword());

        String photoUrl = "src/main/resources/images/profile_" + userInformation.getSoCMND() + ".png";
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
        birthdayText.setMouseTransparent(!isEditable);

        passwordField.setDisable(!isEditable);
        passwordText.setEditable(isEditable);

        maleButton.setMouseTransparent(!isEditable);
        femaleButton.setMouseTransparent(!isEditable);
        editPhotoButton.setDisable(!isEditable);
    }

    private void toggleEditButtons(boolean isEditing) {
        saveButton.setVisible(isEditing);
        cancelButton.setVisible(isEditing);
        editPhotoButton.setVisible(isEditing);
        editProfileButton.setVisible(!isEditing);
    }

    private void editProfile() {
        passwordBeforeEdit = passwordField.getText();
        setEditable(true);
        toggleEditButtons(true);

        passwordField.setVisible(false);
        passwordText.setVisible(true);
        passwordText.setText(passwordField.getText());
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

    private void saveChanges() {
        String name = nameText.getText().trim();
        String id = idText.getText().trim();
        String phone = phoneText.getText().trim();
        String email = emailText.getText().trim();
        String hometown = hometownText.getText().trim();
        String address = addressText.getText().trim();
        String username = usernameText.getText().trim();
        String password = passwordText.getText().trim();
        LocalDate birthday = birthdayText.getValue();

        if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                hometown.isEmpty() || address.isEmpty() || username.isEmpty() ||
                password.isEmpty() || birthday == null ||
                (!maleButton.isSelected() && !femaleButton.isSelected())) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all the fields.");
            return;
        }

        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Weak Password", "Password must be at least 6 characters long.");
            return;
        }

        passwordField.setText(password);
        setEditable(false);
        toggleEditButtons(false);

        new Thread(() -> {
            LogManager logManager = new LogManager();
            userInformation.setTen(name);
            userInformation.setSoCMND(id);
            userInformation.setDienThoai(phone);
            userInformation.setEmail(email);
            userInformation.setQueQuan(hometown);
            userInformation.setDiaChi(address);
            userInformation.setNgaySinh(Date.valueOf(birthday));
            userInformation.setGioiTinh(maleButton.isSelected() ? "Male" : "Female");

            logManager.savePassword(username, password);
            UserInformationDAO.updateUserInformation(userInformation, userCredentials);
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Your profile has been updated successfully!");
            });
        }).start();
    }

    private void cancelEdit() {
        passwordField.setText(passwordBeforeEdit);
        setEditable(false);
        toggleEditButtons(false);

        passwordField.setVisible(true);
        passwordText.setVisible(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}