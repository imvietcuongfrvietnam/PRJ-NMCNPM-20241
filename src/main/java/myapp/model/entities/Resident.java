package myapp.model.entities;

public class Resident {
    private String residentId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String householdId; // ID hộ gia đình

    public Resident(String residentId, String name, String dateOfBirth, String gender, String householdId) {
        this.residentId = residentId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.householdId = householdId;
    }

    // Getter và Setter
    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }
}
