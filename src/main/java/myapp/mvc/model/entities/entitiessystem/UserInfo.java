package myapp.mvc.model.entities.entitiessystem;

public class UserInfo {
    private String name;
    private String birthday;
    private String id;
    private String phone;
    private String email;
    private String hometown;

    // Constructor mặc định
    public UserInfo() {}

    // Constructor đầy đủ
    public UserInfo(String name, String birthday, String id, String phone, String email, String hometown) {
        this.name = name;
        this.birthday = birthday;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.hometown = hometown;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", hometown='" + hometown + '\'' +
                '}';
    }
}
