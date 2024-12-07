package myapp.model.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.model.entities.entitiesdb.UserInformation;

import java.io.File;
import java.io.IOException;

public class LogReader {
    private String logFilePath;
    /**
     * Đọc thông tin username từ file JSON.
     *
     * @return Tên người dùng từ file JSON.
     */
    public static String getUserName(){
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Đọc nội dung file và ánh xạ vào đối tượng UserCredentials
            UserInformation userInformation = mapper.readValue(file, UserInformation.class);
            // Hiển thị thông tin
            return userInformation.getTen();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file savepassword.json.");
        }

        return null;
    }
}
