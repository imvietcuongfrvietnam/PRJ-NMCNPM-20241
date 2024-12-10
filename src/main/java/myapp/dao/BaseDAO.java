package myapp.dao;

import javafx.scene.control.Alert;

/**
 * Lớp cơ sở (BaseDAO) cung cấp các phương thức tiện ích để xử lý lỗi và hiển thị thông báo.
 */
public class BaseDAO {

    /**
     * Hiển thị một thông báo lỗi dưới dạng cửa sổ pop-up.
     *
     * @param title Tiêu đề của cửa sổ thông báo.
     * @param headerText Tiêu đề phần thân thông báo.
     * @param contentText Nội dung chi tiết thông báo lỗi.
     */
    public static void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
