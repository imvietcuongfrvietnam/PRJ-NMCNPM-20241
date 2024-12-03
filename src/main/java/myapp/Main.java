package myapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import myapp.mvc.model.connectdb.SQLConnector;

import static org.junit.internal.Classes.getClass;

public class Main extends Application {
    @Override

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Button.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hệ thống quản lý thu phí chung cư BlueMoon");
        Image icon = new Image(getClass().getResourceAsStream("/image/Icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        SQLConnector sqlConnector = new SQLConnector();
        if(sqlConnector.checkConnection()){
            System.out.println("Kết nối thanh công");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
