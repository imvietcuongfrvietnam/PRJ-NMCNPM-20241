import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private ImageView avatarImageView;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Label emailLabel;

    @FXML
    private Button returnMainScreenButton;

    @FXML
    private void initialize() {
        // Khởi tạo thông tin người dùng, ví dụ:
        userNameLabel.setText("Nguyen Van A");
        emailLabel.setText("nva@gmail.com");
        // Thiết lập ảnh đại diện
        avatarImageView.setImage(new javafx.scene.image.Image("/Image/avatar.png"));
    }

    @FXML
    private void handleReturnMainScreen() {
        // Logic để trở về màn hình chính
        System.out.println("Trở về màn hình chính");
        // Bạn có thể thêm mã ở đây để thay đổi cảnh hoặc bất kỳ logic nào khác.
    }
}
