package myapp.model.entities.entitiessystem;

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

    public UserInfo(String name, String gender, String birthday, String id, String phone, String email, String hometown, String address) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.hometown = hometown;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
