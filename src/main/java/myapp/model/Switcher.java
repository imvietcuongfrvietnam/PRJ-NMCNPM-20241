package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Switcher {
    private static Stage stage;

    // Hàm thiết lập Stage
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }



    // Hàm chuyển cảnh chung, sử dụng Objects.requireNonNull cho chắc chắn
    private void switchScene(String fxmlPath) {
        try {
            // Sử dụng đường dẫn trực tiếp từ resources
            var resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("Không tìm thấy tệp FXML: " + fxmlPath);
            }

            Parent root = FXMLLoader.load(resource);
            stage.setScene(new Scene(root, 800, 600)); // thiết lập kích thước theo yêu cầu
            stage.show();
        } catch (IOException e) {
            System.err.println("Lỗi khi tải FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Khi gọi hàm, sửa lại đường dẫn như sau:
    public void goSignUp() {
        switchScene("/SignUp.fxml");
    }

    public void goHomePage() {
        switchScene("/HomePage.fxml");
    }

}

