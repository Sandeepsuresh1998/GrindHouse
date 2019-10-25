import java.util.Date;
import java.util.Map;

public class Order {

    public Map<MenuItem, Integer> itemsPurchased;
    public Date purchaseTime;

    //getter functions
    public Date getPurchaseTime() { return purchaseTime; }
    public Map<MenuItem, Integer> getItemsPurchased() { return itemsPurchased; }

    //setter functions
    public void setItemsPurchased(Map<MenuItem, Integer> itemsPurchased) { this.itemsPurchased = itemsPurchased; }
    public void setPurchaseTime(Date purchaseTime) { this.purchaseTime = purchaseTime; }

    //return the quantity of an item purchased
    public Integer getItemQuantity(MenuItem mi) {
        Integer quantity = itemsPurchased.get(mi);
        return quantity;
    }

}
