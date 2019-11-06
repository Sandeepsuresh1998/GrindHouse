package model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import model.MenuItem;
import model.Order;

public class Store {

    private int storeID;
    private String name;
    private float longitude;
    private float latitude;
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> purchases;
    private boolean isVerified;
    private Bitmap verifPic;

    //constructor
    public Store(int storeID, float longitude, float latitude, String name, boolean isVerified, Bitmap verifPic){
        this.storeID = storeID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.isVerified = isVerified;
        this.verifPic = verifPic;
        //generate consecutive number for storeID -> add to database
    }

    //getter functions
    public int getStoreID() { return storeID; }
    public ArrayList<MenuItem> getMenu() { return menu; }
    public ArrayList<Order> getPurchases() { return purchases; }
    public float getLongitude() {return longitude; }
    public float getLatitude() { return latitude; }
    public String getName() { return name; }
    public boolean isVerified() { return isVerified; }
    public Bitmap getVerifPic() { return verifPic; }


    //setter functions
    public void setStoreID(int storeID) { this.storeID = storeID; }
    public void setMenu(ArrayList<MenuItem> menu) { this.menu = menu; }
    public void setPurchases(ArrayList<Order> purchases) { this.purchases = purchases; }
    public void setLongitude(float longitude) { this.longitude = longitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }
    public void setName(String name) { this.name = name; }
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }
    public void setVerifPic(Bitmap verifPic) { this.verifPic = verifPic; }

    //allow merchant to see all the purchases made at this store
    //classified customer information is not included (such as name, id, etc.)
    public void seePurchases(){

    }








}
