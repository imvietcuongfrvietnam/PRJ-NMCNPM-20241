package test;

import myapp.model.object.Resident;
import org.junit.Test;
import myapp.model.object.Household;

import static org.junit.Assert.assertTrue;

public class HouseholdTest {

    private final Household household = new Household();

    @Test
    public void containsAnAddedResident() {
        Resident resident = new Resident();
        Household.add(resident);
        assertTrue(household.contains(resident));

    }
    @Test
    public void ownsAnAddedVehicle(){
        Vehicle vehicle = new Vehicle();
        Household.add(vehicle);
        assertTrue(household.own(vehicle));
    }

}
