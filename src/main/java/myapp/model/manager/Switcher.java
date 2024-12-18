package myapp.model.manager;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myapp.controller.BaseController;

import java.io.IOException;

/**
 * Lớp quản lý chuyển đổi giữa các màn hình (scene) trong ứng dụng.
 */
public class Switcher {

    /**
     * Phương thức chuyển đổi scene.
     *
     * @param event      Sự kiện kích hoạt (thường là sự kiện nhấn nút).
     * @param fxmlPath   Đường dẫn tới tệp FXML (bắt đầu từ thư mục "view").
     * @param controller Đối tượng controller (có thể là null nếu không cần gán controller tùy chỉnh).
     * @throws IOException Nếu không thể tải tệp FXML.
     */
    private void switchScene(Event event, String fxmlPath, BaseController controller) throws IOException {
        // Tải tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlPath));
        // Tải root từ tệp FXML
        Parent root = loader.load();

        // Lấy stage hiện tại từ sự kiện
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Tạo scene mới và hiển thị
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Phương thức chuyển đến một trang dựa trên tệp FXML.
     *
     * @param event    Sự kiện kích hoạt.
     * @param fxmlPath Tên tệp FXML (ví dụ: "Main.fxml").
     * @throws IOException Nếu xảy ra lỗi khi tải tệp FXML.
     */
    public void switchToPage(Event event, String fxmlPath) throws IOException {
        this.switchScene(event, fxmlPath, null);
    }

    /**
     * Phương thức chuyển đến một trang với controller tùy chỉnh.
     *
     * @param event      Sự kiện kích hoạt.
     * @param fxmlPath   Tên tệp FXML (ví dụ: "Main.fxml").
     * @param controller Đối tượng controller cần gán.
     * @throws IOException Nếu xảy ra lỗi khi tải tệp FXML.
     */
    public void switchToPage(Event event, String fxmlPath, BaseController controller) throws IOException {
        this.switchScene(event, fxmlPath, controller);
    }

    /**
     * Chuyển đến trang chủ.
     */
    public void goHomePage(Event event, BaseController mainController) throws IOException {
        this.switchToPage(event, "Main.fxml", mainController);
    }

    /**
     * Chuyển đến trang đăng nhập.
     */
    public void goLogInPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "LogIn.fxml", baseController);
    }

    /**
     * Chuyển đến trang đăng ký.
     */
    public void goSignUpPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "SignUp.fxml", baseController);
    }

    /**
     * Chuyển đến danh sách cư dân.
     */
    public void goListOfResidentsPage(Object event, BaseController baseController) throws IOException {
        this.switchToPage((Event) event, "ListOfResidents.fxml", baseController);
    }

    /**
     * Chuyển đến danh sách hộ gia đình.
     */
    public void goListOfHouseholdPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "ListOfHouseHolds.fxml", baseController);
    }

    /**
     * Chuyển đến trang quản lý phí.
     */
    public void goFeeManagementPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "ListOfFees.fxml", baseController);
    }

    /**
     * Chuyển đến trang quên mật khẩu.
     */
    public void goForgotPasswordPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "ForgotPassword.fxml", baseController);
    }

    /**
     * Chuyển đến trang cài đặt.
     */
    public void goSettingPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "Setting.fxml", baseController);
    }

    /**
     * Chuyển đến danh sách căn hộ.
     */
    public void goListOfApartmentPage(Event event, BaseController baseController) throws IOException {
        this.switchToPage(event, "ListOfApartments.fxml", baseController);
    }
    public void goListOfVehiclePage(Event event, BaseController mainController) throws IOException {
        this.switchToPage(event, "ListOfVehicles.fxml", mainController);
    }

    public void goBillManagementPage(Event event, BaseController mainController) throws IOException {
        this.switchToPage(event, "ListOfBills.fxml", mainController);
    }}
