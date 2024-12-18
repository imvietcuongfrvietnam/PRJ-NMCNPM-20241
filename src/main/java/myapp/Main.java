package myapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Thread javafxThread;  // Lưu trữ thread của JavaFX

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Lưu trữ thread hiện tại (thread của JavaFX)
        javafxThread = Thread.currentThread();

        // Tạo giao diện người dùng
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hệ thống quản lý thu phí chung cư BlueMoon");

        // Đặt icon cho ứng dụng
        Image icon = new Image(getClass().getResourceAsStream("/image/Icon.png"));
        primaryStage.getIcons().add(icon);

        // Hiển thị giao diện
        primaryStage.show();

        // Tạo một thread riêng để kiểm tra luồng JavaFX
        Thread checkThread = new Thread(() -> {
            try {
                // Kiểm tra trạng thái của luồng JavaFX
                while (javafxThread.isAlive()) {
                    System.out.println("JavaFX thread is alive...");
                    Thread.sleep(10000); // Kiểm tra mỗi giây
                }

                // Nếu luồng JavaFX kết thúc
                Platform.runLater(() -> {
                    System.out.println("JavaFX thread has stopped.");
                });

            } catch (InterruptedException e) {

            }
        });

        // Bắt đầu thread kiểm tra
        checkThread.start();

        // Khi tắt ứng dụng, gọi Platform.exit để thông báo JavaFX thread đã tắt
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();  // Thoát JavaFX
            checkThread.interrupt(); // Ngắt luồng kiểm tra khi JavaFX kết thúc
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
