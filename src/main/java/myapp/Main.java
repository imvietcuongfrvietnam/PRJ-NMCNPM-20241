package myapp;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import myapp.model.manager.Switcher;


public class Main extends Application {
    int isSuspend = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            Switcher.setStage(primaryStage);
            // load the FXML resource
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));



            // create a scene with specific size
            Scene scene = new Scene(root, 1000, 800);
            primaryStage.setScene(scene);

            // prevent resizing
            primaryStage.setResizable(true);
            primaryStage.setTitle("Blue Moon Management Fee System");

            // set the icon
            // Set the icon using getResourceAsStream
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Logo.png"))));

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
