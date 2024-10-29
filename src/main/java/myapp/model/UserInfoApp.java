package myapp.model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.geometry.Insets;

public class UserInfoApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Nạp tệp FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/UserInfo.fxml"));
            Parent root = loader.load();

            // Tạo Scene và hiển thị
            Scene scene = new Scene(root, 300, 400);
            primaryStage.setTitle("Thông Tin Tài Khoản");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // In ra chi tiết lỗi
            System.out.println("Lỗi khi khởi tạo ứng dụng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
