package model;

import java.util.Date;
import java.util.Map;

import model.MenuItem;

public class Order {
    private int orderID;
    private int itemID;
    private int quantity;
    private int caffeine;
    private int orderTime;

    public Order(int orderID, int itemID, int quantity, int caffeine, int orderTime) {
        setOrderID(orderID);
        setItemID(itemID);
        setQuantity(quantity);
        setCaffeine(caffeine);
        setOrderTime(orderTime);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(int caffeine) {
        this.caffeine = caffeine;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

}
