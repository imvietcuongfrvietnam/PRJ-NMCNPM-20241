package myapp.model.manager;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendCodeToEmailManager {
    public static void sendCode(String destination, String code) {
        String source = "vietcuong2k182@gmail.com";
        String username = "vietcuong2k182@gmail.com";
        String password = "inqpm pnaok lnsyl pdtyn"; // Mật khẩu App cần xác thực
        String host = "smtp.gmail.com";
        Properties pros = new Properties();

        // Cấu hình SMTP và TLS
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.smtp.host", host);
        pros.put("mail.smtp.port", "587");

        // Tạo Authenticator để xác thực
        Authenticator authenticator = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Thiết lập session
        Session session = Session.getInstance(pros, authenticator);
        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Thiết lập người gửi
            message.setFrom(new InternetAddress(source));

            // Thiết lập người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));

            // Thiết lập tiêu đề email
            message.setSubject("Your verify Email!");

            // Thiết lập nội dung email
            message.setContent("Do not send your code for everyone else, your code: "+ code, "text/html");

            // Gửi email
            Transport.send(message);

            System.out.println("Email Sent Successfully!");

        } catch (MessagingException e) {

        }
    }
}
