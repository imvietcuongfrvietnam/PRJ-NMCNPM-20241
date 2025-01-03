package myapp.model.entities.entitiesdb;

import java.sql.Date;

public class Resident {
    private String name;
    private String gender;
    private Date birthday; // Sử dụng java.sql.Date
    private String IDcard;
    private String hometown;
    private String phone;
    private String occupation;
    private String ethnicity;
    private String nationality;
    private String education;
    private String status;
    private String additionalInfo;
    private String houseHoldID;

    // Constructor đầy đủ
    public Resident(String name, String gender, Date birthday, String IDcard, String hometown,
                    String phone, String occupation, String ethnicity, String nationality,
                    String education, String status, String additionalInfo, String houseHoldID) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.IDcard = IDcard;
        this.hometown = hometown;
        this.phone = phone;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
        this.nationality = nationality;
        this.education = education;
        this.status = status;
        this.additionalInfo = additionalInfo;
        this.houseHoldID = houseHoldID;
    }

    // Constructor đơn giản hơn
    public Resident(String name, String gender, Date birthday, String IDcard) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.IDcard = IDcard;
    }

    public Resident(String name, String IDcard) {
        this.name = name;
        this.IDcard = IDcard;
    }

    // Getter và Setter
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getHouseHoldID() {
        return houseHoldID;
    }

    public void setHouseHoldID(String houseHoldID) {
        this.houseHoldID = houseHoldID;
    }
}
