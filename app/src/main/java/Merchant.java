import java.util.ArrayList;


public class Merchant extends User {

    private int merchantID;
    private ArrayList<Store> storesOwned;

    //getter functions
    public int getMerchantID(){ return merchantID; }
    public ArrayList<Store> getStoresOwned(){
        return storesOwned;
    }

    //setter functions
    public void setMerchantID(int merchantID) { this.merchantID = merchantID; }
    public void setStoresOwned(ArrayList<Store> storesOwned) { this.storesOwned = storesOwned; }

    //merchant adds a new store
    public void newStore(){
        Store newStore = new Store(merchantID);
        //add to database
    }

    //merchant removes one of their existing stores
    public void deleteStore(){
        //remove from database
    }

    //seems a little too broad - shouldn't we just have functions within store to change attributes?
    public void editStore(){


    }

}
