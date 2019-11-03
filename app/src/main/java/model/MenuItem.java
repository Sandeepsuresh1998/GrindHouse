package model;

import android.view.Menu;

public class MenuItem {

    private Integer itemID;
    private String name;
    private String size;
    private String timeCreated;
    private Integer calories;
    private Integer caffeine;
    private Double price;

    public MenuItem(Integer itemID, String name, Integer calories, String size, Integer caffeine, Double price, String timeCreated) {
        setID(itemID);
        setName(name);
        setCalories(calories);
        setPrice(price);
        setSize(size);
        setCaffeine(caffeine);
        setTimeCreated(timeCreated);
    }

    //getter functions
    public Integer getID() { return itemID; }
    public String getName() { return name; }
    public String getSize() { return size; }
    public String getTimeCreated() { return timeCreated; }
    public Integer getCalories() { return calories; }
    public Integer getCaffeine() { return caffeine; }
    public Double getPrice() { return price; }


    //setter/change functions
    public void setID(Integer id) { this.itemID = id; }
    public void setName(String name) { this.name = name; }
    public void setSize(String size) { this.size = size; }
    public void setTimeCreated(String timeCreated) { this.timeCreated = timeCreated; }
    public void setCalories(Integer calories) { this.calories = calories; }
    public void setCaffeine(Integer caffeine) { this.caffeine = caffeine; }
    public void setPrice(Double price) { this.price = price; }

}
