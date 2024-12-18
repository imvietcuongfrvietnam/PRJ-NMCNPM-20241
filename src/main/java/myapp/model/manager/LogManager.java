package myapp.model.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import myapp.dao.UserInformationDAO;
import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;

import java.io.File;
import java.io.IOException;

public class LogManager {
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
    /**
     * Lưu thông tin người dùng đăng nhập vào hệ thống dưới dạng JSON.
     * Thông tin này bao gồm các trường cơ bản của người dùng như số CMND, tên, số điện thoại và email.
     *
     * @param username Tên đăng nhập của người dùng.
     * @return true nếu luu thành công false ngược lại
     */
    public boolean saveUserInfo(String username) {
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode userNode = mapper.createObjectNode();

        UserInformation userInformation = UserInformationDAO.selectByUsername(username);

        userNode.put("soCMND", userInformation.getSoCMND());
        userNode.put("ten", userInformation.getTen());
        userNode.put("dienThoai", userInformation.getDienThoai());
        userNode.put("email", userInformation.getEmail());
        userNode.put("queQuan", userInformation.getQueQuan());
        userNode.put("diaChi", userInformation.getDiaChi());
        userNode.put("gioiTinh", userInformation.getGioiTinh());
        userNode.put("ngaySinh", userInformation.getNgaySinh().toString());  // Chuyển đổi Date thành String

        try {
            // Ghi vào file JSON
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, userNode);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Hiển thị thông tin đăng nhập được lưu trước đó (nếu có) từ tệp JSON.
     */
    public UserCredentials credentialsSaved() {
        File file = new File("src/main/resources/logs/savepassword.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserCredentials credentials = mapper.readValue(file, UserCredentials.class);
            return credentials;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
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
    public  UserInformation readUserInfo() {
        File file = new File("src/main/resources/logs/userinfo.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, UserInformation.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file userinfo.json.");
        }

        return null;
    }
    public  UserCredentials readUserCredentials() {
        File file = new File("src/main/resources/logs/savepassword.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, UserCredentials.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể đọc file savepassword.json.");
        }

        return null;
    }

}