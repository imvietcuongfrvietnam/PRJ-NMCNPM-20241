package myapp.model.manager;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myapp.controller.BaseController;
import myapp.controller.SignUpController;

import java.io.IOException;

public class Switcher {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    private void switchScene(BaseController baseController, Event event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/"+fxmlPath));
        root = loader.load();
        baseController = loader.getController();
        baseController.initialize();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goHomePage(BaseController baseController, Event event) throws IOException {
        this.switchScene(baseController, event, "HomePage.fxml");
    }
    public  void goLogInPage(BaseController baseController, Event event) throws IOException {
        this.switchScene(baseController, event, "LogIn.fxml");
    }
}
