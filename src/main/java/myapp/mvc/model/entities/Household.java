package myapp.mvc.model.entities;

import java.util.List;

public class Household {
    private String householdId;
    private String address;
    private double apartmentSize; // Diện tích căn hộ
    private List<Resident> residents; // Danh sách cư dân trong hộ

    public Household(String householdId, String address, double apartmentSize, List<Resident> residents) {
        this.householdId = householdId;
        this.address = address;
        this.apartmentSize = apartmentSize;
        this.residents = residents;
    }

    // Getter và Setter
    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(double apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }
}
