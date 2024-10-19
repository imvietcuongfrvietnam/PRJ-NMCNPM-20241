import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAccountScreen extends JFrame {
    private JTextField tenField, soCMNDField, ngaySinhField, emailField, queQuanField, dienThoaiField;
    private JLabel hinhDaiDienLabel;
    private JButton uploadButton, returnMainScreenButton;

    public UserAccountScreen() {
        setTitle("Thông tin tài khoản");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        // Thông tin tài khoản
        add(new JLabel("Tên:"));
        tenField = new JTextField();
        add(tenField);

        add(new JLabel("Số CMND:"));
        soCMNDField = new JTextField();
        add(soCMNDField);

        add(new JLabel("Ngày Sinh:"));
        ngaySinhField = new JTextField();
        add(ngaySinhField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Quê Quán:"));
        queQuanField = new JTextField();
        add(queQuanField);

        add(new JLabel("Điện Thoại:"));
        dienThoaiField = new JTextField();
        add(dienThoaiField);

        // Ảnh đại diện
        add(new JLabel("Ảnh Đại Diện:"));
        hinhDaiDienLabel = new JLabel();
        hinhDaiDienLabel.setPreferredSize(new Dimension(100, 100));
        add(hinhDaiDienLabel);

        uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code để upload ảnh và hiển thị
                // Ví dụ: Chọn ảnh từ máy tính và hiển thị
            }
        });
        add(uploadButton);

        // Nút trở về màn hình chính
        returnMainScreenButton = new JButton("Return Main Screen");
        returnMainScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code để quay lại màn hình chính
                dispose(); // Đóng màn hình hiện tại
                // Mở màn hình chính ở đây
            }
        });
        add(returnMainScreenButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserAccountScreen screen = new UserAccountScreen();
            screen.setVisible(true);
        });
    }
}
