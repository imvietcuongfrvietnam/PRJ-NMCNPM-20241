package myapp.model.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Switcher {

    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchTo(String fxmlFile) {
        try {
            // Đảm bảo đường dẫn tới file FXML là chính xác
            Parent root = FXMLLoader.load(Switcher.class.getResource("/myapp/view/" + fxmlFile));
            stage.setScene(new Scene(root, 1920, 1080));
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }

    // Hàm chuyển đến trang HomePage
    public static void goHomePage() {
        switchTo("HomePage.fxml"); // Gọi hàm switchTo với tên file FXML trang chính
    }

    // Có thể thêm các hàm chuyển đổi khác nếu cần
}
