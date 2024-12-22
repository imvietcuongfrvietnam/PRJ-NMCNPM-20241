package myapp.model.entities.entitiesdb;

public class UserAccount {
    private int userID;
    private String role;
    private String username;
    private String password;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String IDcard;
    private String phone;
    private String email;
    private String hometown;
    private String address;

    public UserAccount(int userID, String role, String username, String password, String name, String gender, String dateOfBirth, String IDcard, String phone, String email, String hometown, String address) {
        this.userID = userID;
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.IDcard = IDcard;
        this.phone = phone;
        this.email = email;
        this.hometown = hometown;
        this.address = address;
    }

    public UserAccount() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
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
