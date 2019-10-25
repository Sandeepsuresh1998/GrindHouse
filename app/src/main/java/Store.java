import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Store {

    public int storeID;
    public LatLng location;
    public ArrayList<MenuItem> menu;
    public ArrayList<Order> purchases;
    private int merchantID;

    //constructor
    public Store(int merchantID){
        this.merchantID = merchantID;
        //generate consecutive number for storeID -> add to database
    }

    //getter functions
    public int getMerchantID() { return merchantID; }
    public int getStoreID() { return storeID; }
    public LatLng getLocation() { return location; }
    public ArrayList<MenuItem> getMenu() { return menu; }
    public ArrayList<Order> getPurchases() { return purchases; }

    //setter functions
    public void setStoreID(int storeID) { this.storeID = storeID; }
    public void setLocation(LatLng location) { this.location = location; }
    public void setMenu(ArrayList<MenuItem> menu) { this.menu = menu; }
    public void setPurchases(ArrayList<Order> purchases) { this.purchases = purchases; }

    //allow merchant to see all the purchases made at this store
    //classified customer information is not included (such as name, id, etc.)
    public void seePurchases(){

    }








}
