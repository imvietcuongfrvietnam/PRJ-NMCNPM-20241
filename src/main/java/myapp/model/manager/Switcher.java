package myapp.model.manager;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myapp.controller.*;

import java.io.IOException;

public class Switcher {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    // Phương thức chuyển cảnh chung
    private void switchScene(Event event, String fxmlPath, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlPath));

        // Set controller cho mỗi màn hình FXML
        loader.setController(controller);

        // Nạp FXML và tạo Scene
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // Hiển thị Scene
        stage.setScene(scene);
        stage.show();
    }

    // Phương thức chuyển đến trang chủ
    public void goHomePage(LogInController mainController, Event event) throws IOException {
        this.switchScene(event, "Main.fxml", mainController);
    }

    // Phương thức chuyển đến trang đăng nhập
    public void goLogInPage(BaseController baseController, Event event) throws IOException {
        this.switchScene(event, "LogIn.fxml", baseController);
    }

    // Phương thức chuyển đến trang đăng ký
    public void goSignUpPage(BaseController baseController, Event event) throws IOException {
        this.switchScene(event, "SignUp.fxml", baseController);
    }

    // Phương thức chuyển đến danh sách cư dân
    public void goListOfResidentsPage(Event event) throws IOException {
        this.switchScene(event, "ListOfResidents.fxml", null); // Không có controller riêng
    }

    // Phương thức chuyển đến danh sách hộ gia đình
    public void goListOfHouseholdPage(Event event) throws IOException {
        this.switchScene(event, "ListOfHouseHolds.fxml", null); // Không có controller riêng
    }

    // Phương thức chuyển đến quản lý phí
    public void goFeeManagementPage(Event event) throws IOException {
        this.switchScene(event, "ListOfFees.fxml", null);
    }
}
