package myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import myapp.model.communicatedb.update.UserAccountUpdate;
import myapp.model.entities.entitiesdb.UserAccount;
import myapp.model.manager.LogManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class SettingController extends BaseController {
    @FXML
    private Button cancelButton, saveButton,editPhotoButton, editProfileButton;
    @FXML
    private TextField userIDText, roleText, nameText, idText, phoneText, emailText, hometownText, addressText, usernameText, passwordText;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton maleButton;
    @FXML
    private RadioButton femaleButton;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private Circle photoProfile;
    @FXML
    private PasswordField passwordField;

    private String passwordBeforeEdit;
    private Image profileImageBeforeEdit;

    @Override
    public void initialize() {
        super.initialize();

        userIDText.setText(String.valueOf(LogManager.getUser().getUserID()));
        roleText.setText(LogManager.getUser().getRole());
        usernameText.setText(LogManager.getUser().getUsername());
        passwordText.setText(LogManager.getUser().getPassword());
        passwordField.setText(LogManager.getUser().getPassword());
        nameText.setText(LogManager.getUser().getName());
        for (Toggle toggle : genderGroup.getToggles()) {
            if (toggle instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) toggle;
                if (radioButton.getText().equalsIgnoreCase(LogManager.getUser().getGender())) {
                    genderGroup.selectToggle(radioButton);
                    break;
                }
            }
        }
        birthdayText.setValue(LocalDate.parse(LogManager.getUser().getDateOfBirth()));
        idText.setText(LogManager.getUser().getIDcard());
        phoneText.setText(LogManager.getUser().getPhone());
        emailText.setText(LogManager.getUser().getEmail());
        hometownText.setText(LogManager.getUser().getHometown());
        addressText.setText(LogManager.getUser().getAddress());

        userIDText.setEditable(false);
        roleText.setEditable(false);
        usernameText.setEditable(false);
        passwordText.setEditable(false);
        nameText.setEditable(false);
        maleButton.setMouseTransparent(true);
        femaleButton.setMouseTransparent(true);
        birthdayText.setMouseTransparent(true);
        idText.setEditable(false);
        phoneText.setEditable(false);
        emailText.setEditable(false);
        hometownText.setEditable(false);
        addressText.setEditable(false);

        editProfileButton.setOnAction(e -> editProfile());
        saveButton.setOnAction(e -> saveChanges());
        cancelButton.setOnAction(e -> cancelEdit());
        editPhotoButton.setOnAction(e -> editPhoto());
    }

    private void editProfile() {
        passwordBeforeEdit = passwordField.getText();
        userIDText.setEditable(false);
        roleText.setEditable(true);
        usernameText.setEditable(true);
        passwordText.setVisible(true);
        passwordText.setText(passwordField.getText());
        passwordText.setEditable(true);
        passwordField.setVisible(false);
        nameText.setEditable(true);
        maleButton.setMouseTransparent(false);
        femaleButton.setMouseTransparent(false);
        birthdayText.setMouseTransparent(false);
        idText.setEditable(true);
        phoneText.setEditable(true);
        emailText.setEditable(true);
        hometownText.setEditable(true);
        addressText.setEditable(true);

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
        userIDText.setEditable(false);
        roleText.setEditable(false);
        usernameText.setEditable(false);
        passwordField.setEditable(false);
        passwordField.setText(passwordText.getText());
        passwordField.setVisible(true);
        passwordText.setVisible(false);
        nameText.setEditable(false);
        maleButton.setMouseTransparent(true);
        femaleButton.setMouseTransparent(true);
        birthdayText.setMouseTransparent(true);
        idText.setEditable(false);
        phoneText.setEditable(false);
        emailText.setEditable(false);
        hometownText.setEditable(false);
        addressText.setEditable(false);

        editProfileButton.setVisible(true);
        editPhotoButton.setVisible(false);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);

        if (userIDText.getText().isEmpty() || roleText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty() ||
                nameText.getText().isEmpty() || (!maleButton.isSelected() && !femaleButton.isSelected()) || birthdayText.getValue() == null ||
                idText.getText().isEmpty() ||  phoneText.getText().isEmpty() || emailText.getText().isEmpty() || hometownText.getText().isEmpty() ||
                addressText.getText().isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin vào các trường thông tin!");
            return;
        }

        int userID = Integer.parseInt(userIDText.getText());
        String role = roleText.getText();
        String name = nameText.getText();
        String gender = maleButton.isSelected() ? "Nam" : "Nữ";
        String dateOfBirth = birthdayText.getValue().toString();
        String IDcard = idText.getText();
        String phone = phoneText.getText();
        String email = emailText.getText();
        String hometown = hometownText.getText();
        String address = addressText.getText();
        String username = usernameText.getText();
        String password = passwordField.getText();

        UserAccount updatedUser = new UserAccount(userID, role,username, password, name, gender, dateOfBirth, IDcard, phone, email, hometown, address);
        try {
            UserAccountUpdate.updateUser(updatedUser);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thông tin tài khoản đã được cập nhật!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật thông tin vào cơ sở dữ liệu!");
        }

        File file = new File("src/main/resources/logs/userInformation.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, updatedUser);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể lưu thông tin vào file JSON!");
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
