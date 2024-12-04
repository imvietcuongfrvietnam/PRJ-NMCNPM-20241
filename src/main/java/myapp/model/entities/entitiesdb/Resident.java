package myapp.model.entities.entitiesdb;

import java.time.LocalDate;
import java.time.LocalTime;

public class Resident {
    private String name;
    private String gender;
    private String birthday;
    private String IDcard;
    private String hometown;
    private String occupation;
    private String ethnicity;
    private String nationality;
    private String education;
    private String status;
    private String additionalInfo;
    private String houseHoldID;
    private String relation;

    public Resident(String name, String gender, String birthday, String IDcard, String hometown, String occupation, String ethnicity, String nationality, String education, String status, String additionalInfo, String houseHoldID) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.IDcard = IDcard;
        this.hometown = hometown;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
        this.nationality = nationality;
        this.education = education;
        this.status = status;
        this.additionalInfo = additionalInfo;
        this.houseHoldID = houseHoldID;
    }

    public Resident() {
    }

    public Resident(String name, String gender, String birthday, String IDcard) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.IDcard = IDcard;
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

    public LocalTime getBirthDate() {
        return null;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
