package myapp.model.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import myapp.model.entities.entitiesdb.UserAccount;

import java.io.File;
import java.io.IOException;

public class LogManager {
    /**
     * Đọc thông tin username từ file JSON.
     *
     * @return Tên người dùng từ file JSON.
     */
//    public static String getName() {
//        File file = new File("src/main/resources/logs/userInformation.json");
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            UserAccount userAccount = mapper.readValue(file, UserAccount.class);
//            return userAccount.getName();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Không thể đọc file userInformation.json.");
//        }
//        return null;
//    }

    public static UserAccount getUser() {
        File file = new File("src/main/resources/logs/userInformation.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserAccount userAccount = mapper.readValue(file, UserAccount.class);
            return userAccount;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file userInformation.json.");
        }
        return null;
    }

    /**
     * Lưu thông tin đăng nhập của người dùng (nếu được chọn) vào tệp JSON.
     * Thông tin được lưu trữ trong tệp `savepassword.json`.
     */
    public boolean savePassword(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();
        userNode.put("username", username);
        userNode.put("password", password);
        try {
            File file = new File("src/main/resources/logs/savepassword.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Đọc thông tin người dùng từ file JSON và trả về đối tượng UserInformation.
     *
     * @return Đối tượng UserInformation chứa thông tin người dùng từ file JSON.
     */
    public  UserAccount readUserInfo() {
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, UserAccount.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file userinfo.json.");
        }

        return null;
    }

}