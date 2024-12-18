package myapp.model.manager;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatDatePresent {
    public static String formatDate(Date date) {
        try {
            // Định dạng đầu ra
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Chuyển đổi java.sql.Date thành LocalDate và định dạng
            LocalDate localDate = date.toLocalDate();
            return localDate.format(outputFormatter); // Định dạng LocalDate thành String
        } catch (Exception e) {
            // Nếu lỗi xảy ra, trả về giá trị ngày gốc dưới dạng chuỗi
            return date.toString();
        }
    }
}
