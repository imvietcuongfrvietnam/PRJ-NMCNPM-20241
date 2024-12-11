package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import myapp.model.manager.Switcher;

import java.io.IOException;

/**
 * Lớp cơ sở trừu tượng cho các controller hỗ trợ điều hướng giữa các trang trong ứng dụng.
 *
 * Lớp này cung cấp các phương thức cài đặt sự kiện để điều hướng đến các trang chính của ứng dụng
 * thông qua các nút bấm, sử dụng đối tượng {@link Switcher}.
 */
abstract class NavigableController extends BaseController {

    /**
     * Phương thức khởi tạo, gán hành động điều hướng cho các nút bấm trong giao diện.
     */
    @Override
    public void initialize() {
        Switcher switcher = new Switcher();

        // Gán hành động điều hướng đến trang chủ
        homePageButton.setOnAction(event -> navigateSafely(() -> switcher.goHomePage(event, this)));

        // Gán hành động điều hướng đến trang đăng nhập
        logOutButton.setOnAction(event -> navigateSafely(() -> switcher.goLogInPage(event, this)));

        // Gán hành động điều hướng đến trang quản lý cư dân
        residentManagementButton.setOnAction(event -> navigateSafely(() -> switcher.goListOfResidentsPage(event, this)));

        // Gán hành động điều hướng đến trang cài đặt
        settingButton.setOnAction(event -> navigateSafely(() -> switcher.goSettingPage(event, this)));

        // Gán hành động điều hướng đến trang quản lý chung cư
        generalManagementButton.setOnAction(event -> navigateSafely(() -> switcher.goListOfApartmentPage(event, this)));

        // Gán hành động điều hướng đến trang quản lý phí
        feeManagementButton.setOnAction(event -> navigateSafely(() -> switcher.goFeeManagementPage(event, this)));
    }

    /**
     * Thực thi hành động điều hướng một cách an toàn, bắt và xử lý ngoại lệ nếu có.
     *
     * @param navigationAction Hành động điều hướng cần thực thi.
     */
    private void navigateSafely(NavigationAction navigationAction) {
        try {
            navigationAction.execute();
        } catch (IOException e) {
            // Log lỗi hoặc xử lý tùy theo yêu cầu
            throw new RuntimeException("Failed to navigate: " + e.getMessage(), e);
        }
    }

    /**
     * Functional interface đại diện cho một hành động điều hướng có thể ném ngoại lệ.
     */
    @FunctionalInterface
    private interface NavigationAction {
        void execute() throws IOException;
    }

    /** Nút điều hướng đến trang chủ. */
    @FXML
    protected Button homePageButton;

    /** Nút điều hướng đến trang quản lý phí. */
    @FXML
    protected Button feeManagementButton;

    /** Nút điều hướng đến trang quản lý cư dân. */
    @FXML
    protected Button residentManagementButton;

    /** Nút đăng xuất và điều hướng đến trang đăng nhập. */
    @FXML
    protected Button logOutButton;

    /** Nút điều hướng đến trang quản lý chung cư. */
    @FXML
    protected Button generalManagementButton;

    /** Nút điều hướng đến trang cài đặt. */
    @FXML
    protected Button settingButton;
}
