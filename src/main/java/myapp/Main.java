package myapp;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myapp.model.manager.Switcher;

public class Main extends Application {
    int isSuspend = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            Switcher.setStage(primaryStage);
            // load the FXML resource
            File fxmlFile = new File("D:\\IT1\\2024.1\\IT3180\\PRJ-NMCNPM-20241\\src\\main\\java\\myapp\\view\\Login.fxml");
            Parent root = FXMLLoader.load(fxmlFile.toURI().toURL());

            // create a scene with specific size
            Scene scene = new Scene(root, 1920, 1080);
            primaryStage.setScene(scene);

            // prevent resizing
            primaryStage.setResizable(false);
            primaryStage.setTitle("Blue Moon Management Fee System");

            // set the icon
            primaryStage.getIcons().add(new javafx.scene.image.Image("file:D:\\IT1\\2024.1\\IT3180\\PRJ-NMCNPM-20241\\resources\\image\\Logo.png"));

            // show the GUI
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
