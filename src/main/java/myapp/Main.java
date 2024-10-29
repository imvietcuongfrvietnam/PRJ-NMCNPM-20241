import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Switcher;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Switcher.setStage(primaryStage);
            // load the FXML resource
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));



            // create a scene
            primaryStage.setScene(new Scene(root, 800, 600));
            // prevent resizing
            primaryStage.setResizable(false);
            // set the title
            primaryStage.setTitle("Blue Moon Management Fee System");

            // set the icon
            primaryStage.getIcons().add(new javafx.scene.image.Image("/image/Logo.png"));

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