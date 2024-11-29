package myapp.mvc.model.manager;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myapp.mvc.controller.BaseController;
import myapp.mvc.controller.LogInController;
import myapp.mvc.controller.MainController;
import myapp.mvc.controller.UserInfoController;

import java.io.IOException;

public class Switcher {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    private void switchScene(BaseController baseController, Event event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/fxml/" +fxmlPath));
        root = loader.load();
        baseController = loader.getController();
        baseController.initialize();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void goHomePage(BaseController mainController, Event event) throws IOException {
        this.switchScene(mainController, event, "Main.fxml");
    }

    public  void goLogInPage(BaseController baseController, Event event) throws IOException {
        this.switchScene(baseController, event, "LogIn.fxml");
    }
    public  void goSignUpPage(BaseController baseController, Event event) throws IOException {
        this.switchScene(baseController, event, "SignUp.fxml");
    }
}
