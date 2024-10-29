package myapp;
import java.io.IOException;
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
                      primaryStage.setScene(new Scene(root, 800, 600));
            // prevent resizing
            primaryStage.setResizable(false);
            // set the title
            primaryStage.setTitle("Blue Moon Management Fee System");

            // set the icon

            primaryStage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Logo.png"))));


            // show the GUI
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); // In ra ngoại lệ IOException
        }
    }

        public static void main(String[] args) {
        launch(args);
    }
}