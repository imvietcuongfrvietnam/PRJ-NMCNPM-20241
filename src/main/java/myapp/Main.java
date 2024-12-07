package myapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hệ thống quản lý thu phí chung cư BlueMoon");
        Image icon = new Image(getClass().getResourceAsStream("/image/Icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
