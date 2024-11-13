package myapp.model.entities.entitiessystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String name;
    private String gender;
    private String birthday;
    private String id;
    private String phone;
    private String email;
    private String hometown;
    private String address;
    private String username;
    private String password;
}
