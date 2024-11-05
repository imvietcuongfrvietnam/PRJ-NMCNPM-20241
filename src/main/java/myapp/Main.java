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

            // Optional: Add a listener to handle when leaving full screen
            primaryStage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    primaryStage.setWidth(1920);
                    primaryStage.setHeight(1080);
                }
            });

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
