package testing;

import myapp.model.entities.Resident;
import org.junit.Test;
import myapp.model.entities.Household;

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
