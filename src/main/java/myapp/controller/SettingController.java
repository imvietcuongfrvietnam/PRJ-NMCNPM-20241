package myapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
    private Button usernameButton;

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
        this.loadUsernameFromJson();
    }
    private void loadUsernameFromJson() {
        File file = new File("src/main/resources/logs/user.json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            String username = rootNode.path("username").asText();
            usernameButton.setText(username);
        } catch (IOException e) {
            e.printStackTrace();
            usernameButton.setText("Error loading username");
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
