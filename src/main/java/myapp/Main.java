package myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // load the FXML resource
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));

            // create a scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // prevent resizing
            primaryStage.setResizable(false);
            // set the title
            primaryStage.setTitle("Blockchain Search App");

            // set the icon
            InputStream input = getClass().getClassLoader().getResourceAsStream("image/Icon.png");
            if (input == null) {
                System.out.println("Resource not found!");
            } else {
                primaryStage.getIcons().add(new javafx.scene.image.Image(input));
            }

            // open in full screen
            primaryStage.setFullScreen(true);

            // show the GUI
            primaryStage.show();

            // Optional: Add a listener to handle resizing events if necessary
            primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                // Code to handle width changes, if needed
            });

            primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
                // Code to handle height changes, if needed
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
