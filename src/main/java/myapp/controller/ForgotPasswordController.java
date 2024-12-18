package myapp.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import myapp.dao.UserAccountDAO;
import myapp.dao.UserInformationDAO;
import myapp.model.manager.SendCodeToEmailManager;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.util.Random;
/**
 * Bộ điều khiển quản lý quy trình quên mật khẩu trong ứng dụng.
 * Bao gồm các bước gửi mã xác minh, xác minh mã, và đặt lại mật khẩu.
 */

public class ForgotPasswordController implements BaseController {
    /**
     * Khởi tạo các thành phần giao diện và cài đặt các sự kiện cần thiết.
     */
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

    /**
     * Khởi tạo các thành phần giao diện và cài đặt các  kiện
     */
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

        backButton.setOnAction(event ->{
            Switcher switcher = new Switcher();
            try {
                switcher.goLogInPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        verifyButton.setOnAction(e -> verifyCode());

        sendBackButton.setOnAction(e -> sendVerificationCode());

        saveButton.setOnAction(e -> saveNewPassword());

        setupAutoFocusForCodeFields();
    }
    /**
     * Gửi mã xác minh đến email đã nhập.
     * Gồm việc tạo mã, gửi qua email, và chuyển sang giao diện nhập mã xác minh.
     */

    private void sendVerificationCode() {
        if(! UserInformationDAO.checkEmail(email)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not found your email address!");
            alert.setContentText("Please try again, not found your email address");
            alert.show();
            return;
        }
        emailLabel.setText(emailText.getText());
        generateVerificationCode();
        SendCodeToEmailManager.sendCode(email, verificationCode);
        codeExpirationTime = System.currentTimeMillis() + 120000; // Mã xác minh hết hạn sau 2 phút (120 giây)
        notifyLabel.setText("A verification code has been sent to your email. It is valid for 2 minutes.");
        showCodePane();
        clearCodeFields();
    }

    /**
     * Generate the verification Code
     */
    private void generateVerificationCode() {
        Random random = new Random();
        verificationCode = String.format("%06d", random.nextInt(1000000));
    }
    /**
     * Xác minh mã xác minh mà người dùng đã nhập.
     * Hiển thị thông báo phù hợp nếu mã đúng hoặc sai, hoặc đã hết hạn.
     */
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
            codeField1.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            codeField2.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            codeField3.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            codeField4.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            codeField5.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            codeField6.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(e -> showPasswordPane());
            delay.play();
            notifyLabel.setText("Code verified successfully.");
        } else {
            notifyLabel.setText("Invalid verification code. Please try again.");
            codeField1.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
            codeField2.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
            codeField3.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
            codeField4.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
            codeField5.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
            codeField6.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
        }
    }

    /**
     * Kiểm tra xem mật khẩu có đáp ứng các tiêu chí bảo mật không.
     * @param password Mật khẩu cần kiểm tra.
     * @return True nếu mật khẩu đáp ứng các tiêu chí (ít nhất 8 ký tự, có chữ hoa, chữ thường, số và ký tự đặc biệt).
     */
    private boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    /**
     * Lưu mật khẩu mới nếu hợp lệ, sau khi kiểm tra các tiêu chí và xác minh sự khớp nhau của mật khẩu.
     * Cập nhật mật khẩu trong cơ sở dữ liệu qua DAO.
     */
    private void saveNewPassword() {
        String newPassword = newPasswordText.getText();
        String reenteredPassword = reenterPasswordText.getText();
        if (!isStrongPassword(newPassword)) {
            notifyLabel.setText("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.");
            return;
        }
        if (!newPassword.equals(reenteredPassword)) {
            notifyLabel.setText("Passwords do not match. Try again.");
            return;
        }
        try {
            UserAccountDAO.updatePasswordByEmail(email, newPassword);
            notifyLabel.setText("Password has been reset successfully.");
        } catch (Exception e) {
            notifyLabel.setText("An error occurred while resetting the password. Please try again.");
            e.printStackTrace(); // Optional: Use a logging framework in production.
        }
    }



    /**
     * Xóa nội dung trong các ô nhập mã xác minh và đặt lại phong cách của chúng.
     */
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

    /**
     * Cài đặt sự kiện tự động chuyển đổi tiêu điểm giữa các ô nhập mã xác minh.
     * Chuyển sang ô tiếp theo nếu một ký tự được nhập, hoặc giới hạn nhập chỉ 1 ký tự.
     */
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

    /**
     * Hiển thị giao diện nhập email.
     * Đặt trạng thái giao diện để chuẩn bị cho bước đầu tiên của quy trình quên mật khẩu.
     */
    private void showEmailPane() {
        step1Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        emailPane.setVisible(true);
        codePane.setVisible(false);
        passwordPane.setVisible(false);
    }

    /**
     * Hiển thị giao diện nhập mã xác minh.
     * Đặt trạng thái giao diện để chuẩn bị cho bước thứ hai của quy trình quên mật khẩu.
     */

    private void showCodePane() {
        step1Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        line1.setStyle("-fx-stroke: #002060;");
        step2Label.setStyle("-fx-background-color: #002060; -fx-background-radius: 50");
        emailPane.setVisible(false);
        codePane.setVisible(true);
        passwordPane.setVisible(false);
    }

    /**
     * Hiển thị giao diện đặt mật khẩu mới.
     * Đặt trạng thái giao diện để chuẩn bị cho bước cuối cùng của quy trình quên mật khẩu.
     */
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

    /**
     * Kiểm tra định dạng email xem có hợp lệ không.
     * @param email Địa chỉ email cần kiểm tra.
     * @return True nếu email hợp lệ.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}