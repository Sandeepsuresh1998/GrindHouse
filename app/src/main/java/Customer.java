import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Customer extends User {

    private String customerID;
    private ArrayList<Trip> tripsTaken;
    private int caffeineIntake;

    //getter functions
    public String getCustomerID() { return customerID; }
    public ArrayList<Trip> getTripsTaken() { return tripsTaken; }
    public int getCaffeineIntake() { return caffeineIntake; }

    //setter functions
    public void setCustomerID(String customerID) { this.customerID = customerID; }
    public void setTripsTaken(ArrayList<Trip> tripsTaken) { this.tripsTaken = tripsTaken; }
    public void setCaffeineIntake(int caffeineIntake) { this.caffeineIntake = caffeineIntake; }
    
}
