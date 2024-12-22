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
    public void goHomePage(Event event) throws IOException {
        this.switchScene(event, "Main.fxml", null);
    }

    // Phương thức chuyển đến trang đăng nhập
    public void goLogInPage(Event event) throws IOException {
        this.switchScene(event, "LogIn.fxml", null);
    }

    // Phương thức chuyển đến trang đăng ký
    public void goSignUpPage(Event event) throws IOException {
        this.switchScene(event, "SignUp.fxml", null);
    }
    // Phương thức chuyển đến trang quên tài khoản
    public void goForgotPasswordPage(Event event) throws IOException {
        this.switchScene(event, "ForgotPassword.fxml", null);
    }
    // Phương thức chuyển đến danh sách cư dân
    public void goListOfResidentsPage(Event event) throws IOException {
        this.switchScene(event, "ListOfResidents.fxml", null); // Không có controller riêng
    }

    // Phương thức chuyển đến danh sách hộ gia đình
    public void goListOfHouseholdPage(Event event) throws IOException {
        this.switchScene(event, "ListOfHouseHolds.fxml", null); // Không có controller riêng
    }
    // Phương thức chuyển đến danh sách căn hộ
    public void goListOfApartmentPage(Event event) throws IOException {
        this.switchScene(event, "ListOfApartments.fxml", null);
    }
    // Phương thức chuyển đến danh sách phương tiện
    public void goListOfVehiclePage(Event event) throws IOException {
        this.switchScene(event, "ListOfVehicles.fxml", null);
    }
    // Phương thức chuyển đến quản lý khoản phí
    public void goFeeManagementPage(Event event) throws IOException {
        this.switchScene(event, "ListOfFees.fxml", null);
    }
    // Phương thức chuyển đến quản lý hóa đơn
    public void goBillManagementPage(Event event) throws IOException {
        this.switchScene(event, "ListOfBills.fxml", null);
    }
    // Phương thức chuyển đến trang cài đặt
    public void goSettingPage(Event event) throws IOException {
        this.switchScene(event, "Setting.fxml", null);
    }

}
