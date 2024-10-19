import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class UserInfoApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Thông tin người dùng
        String ten = "Nguyen Van A";
        String soCMND = "123456789123";
        String email = "nva@gmail.com";
        String queQuan = "Ho Chi Minh";
        String dienThoai = "0987654321";
        String anhDaiDienPath = "path/to/image.jpg"; // Đường dẫn đến ảnh đại diện

        // Các trường thông tin
        Label lblTen = new Label("Họ Tên: " + ten);
        Label lblSoCMND = new Label("Số CMND: " + soCMND);
        Label lblEmail = new Label("Email: " + email);
        Label lblQueQuan = new Label("Quê Quán: " + queQuan);
        Label lblDienThoai = new Label("Điện Thoại: " + dienThoai);

        // Ảnh đại diện
        ImageView imgView = new ImageView(new Image(anhDaiDienPath));
        imgView.setFitWidth(100);
        imgView.setFitHeight(100);
        imgView.setPreserveRatio(true);

        // Nút trở về màn hình chính
        Button btnReturn = new Button("Trở Về Màn Hình Chính");
        btnReturn.setOnAction(e -> {
            // Logic để trở về màn hình chính
            System.out.println("Trở về màn hình chính...");
        });

        // Bố trí giao diện
        VBox vbox = new VBox(10, imgView, lblTen, lblSoCMND, lblEmail, lblQueQuan, lblDienThoai, btnReturn);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setTitle("Thông Tin Tài Khoản");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
