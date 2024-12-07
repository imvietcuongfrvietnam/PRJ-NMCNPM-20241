package myapp.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import myapp.model.manager.SendCodeToEmailManager;

import java.util.Random;

public class ForgotPasswordController {
    @FXML
    private Label step1Label;
    @FXML
    private Label step2Label;
    @FXML
    private Label step3Label;
    @FXML
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private StackPane emailPane;
    @FXML
    private Label notifyLabel;
    @FXML
    private TextField emailText;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;
    @FXML
    private StackPane codePane;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField codeField1, codeField2, codeField3, codeField4, codeField5, codeField6;
    @FXML
    private Button verifyButton;
    @FXML
    private Button sendBackButton;
    @FXML
    private StackPane passwordPane;
    @FXML
    private TextField newPasswordText;
    @FXML
    private TextField reenterPasswordText;
    @FXML
    private Button saveButton;

    private String verificationCode;
    private String email;
    private long codeExpirationTime; // Thời gian hết hạn mã xác minh

    @FXML
    public void initialize() {
        showEmailPane();
        sendButton.setDisable(true);
        emailText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && isValidEmail(newValue)) {
                sendButton.setDisable(false);
                email = emailText.getText();
            } else {
                sendButton.setDisable(true);
            }
        });
        sendButton.setOnAction(e -> sendVerificationCode());

        backButton.setOnAction(e -> showEmailPane());

        verifyButton.setOnAction(e -> verifyCode());

        sendBackButton.setOnAction(e -> sendVerificationCode());

        saveButton.setOnAction(e -> saveNewPassword());

        setupAutoFocusForCodeFields();
    }

    private void sendVerificationCode() {
        emailLabel.setText(emailText.getText());
        generateVerificationCode();
        SendCodeToEmailManager.sendCode(email, verificationCode);
        codeExpirationTime = System.currentTimeMillis() + 120000; // Mã xác minh hết hạn sau 2 phút (120 giây)
        notifyLabel.setText("A verification code has been sent to your email. It is valid for 2 minutes.");
        showCodePane();
        clearCodeFields();
    }

    private void generateVerificationCode() {
        Random random = new Random();
        verificationCode = String.format("%06d", random.nextInt(1000000));
    }

    private void verifyCode() {
        if (System.currentTimeMillis() > codeExpirationTime) {
            notifyLabel.setText("The verification code has expired. Please request a new code.");
            return;
        }

        StringBuilder inputCode = new StringBuilder();
        inputCode.append(codeField1.getText())
                .append(codeField2.getText())
                .append(codeField3.getText())
                .append(codeField4.getText())
                .append(codeField5.getText())
                .append(codeField6.getText());

        if (inputCode.toString().equals(verificationCode)) {
            codeField1.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            codeField2.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            codeField3.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            codeField4.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            codeField5.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            codeField6.setStyle("-fx-background-color: rgba(0, 255, 0, 0.75);");
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(e -> showPasswordPane());
            delay.play();
            notifyLabel.setText("Code verified successfully.");
        } else {
            notifyLabel.setText("Invalid verification code. Please try again.");
            codeField1.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
            codeField2.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
            codeField3.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
            codeField4.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
            codeField5.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
            codeField6.setStyle("-fx-background-color: rgba(255, 0, 0, 0.75);");
        }
    }

    private void saveNewPassword() {
        String newPassword = newPasswordText.getText();
        String reenteredPassword = reenterPasswordText.getText();
        if (newPassword.equals(reenteredPassword)) {
            // Lưu mật khẩu mới
            notifyLabel.setText("Password has been reset successfully.");
        } else {
            notifyLabel.setText("Passwords do not match. Try again.");
        }
    }

    private void clearCodeFields() {
        codeField1.clear();
        codeField2.clear();
        codeField3.clear();
        codeField4.clear();
        codeField5.clear();
        codeField6.clear();
        codeField1.setStyle("");
        codeField2.setStyle("");
        codeField3.setStyle("");
        codeField4.setStyle("");
        codeField5.setStyle("");
        codeField6.setStyle("");
    }

    private void setupAutoFocusForCodeFields() {
        codeField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                codeField2.requestFocus();
            } else if (newValue.length() > 1) {
                codeField1.setText(newValue.substring(0, 1));
            }
        });
        codeField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                codeField3.requestFocus();
            } else if (newValue.length() > 1) {
                codeField2.setText(newValue.substring(0, 1));
            }
        });
        codeField3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                codeField4.requestFocus();
            } else if (newValue.length() > 1) {
                codeField3.setText(newValue.substring(0, 1));
            }
        });
        codeField4.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                codeField5.requestFocus();
            } else if (newValue.length() > 1) {
                codeField4.setText(newValue.substring(0, 1));
            }
        });
        codeField5.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                codeField6.requestFocus();
            } else if (newValue.length() > 1) {
                codeField5.setText(newValue.substring(0, 1));
            }
        });
        codeField6.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                codeField6.setText(newValue.substring(0, 1));
            }
        });
    }

    private void showEmailPane() {
        step1Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        emailPane.setVisible(true);
        codePane.setVisible(false);
        passwordPane.setVisible(false);
    }

    private void showCodePane() {
        step1Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        line1.setStyle("-fx-stroke: #002060;");
        step2Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        emailPane.setVisible(false);
        codePane.setVisible(true);
        passwordPane.setVisible(false);
    }

    private void showPasswordPane() {
        step1Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        line1.setStyle("-fx-stroke: #002060;");
        step2Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        line2.setStyle("-fx-stroke: #002060;");
        step3Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        emailPane.setVisible(false);
        codePane.setVisible(false);
        passwordPane.setVisible(true);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
