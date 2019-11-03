package model;

import android.view.Menu;

public class MenuItem {

    private Integer itemID;
    private String name;
    private Integer calories;
    private Integer caffeine;
    private Double price;

    public MenuItem(Integer itemID, String name, Integer calories) {
        setID(itemID);
        setName(name);
        setCalories(calories);
    }

    //getter functions
    public Integer getID() { return itemID; }
    public String getName() { return name; }
    public Integer getCalories() { return calories; }
    public Integer getCaffeine() { return caffeine; }
    public Double getPrice() { return price; }


    //setter/change functions
    public void setID(Integer id) { this.itemID = id; }
    public void setName(String name) { this.name = name; }
    public void setCalories(Integer calories) { this.calories = calories; }
    public void setCaffeine(Integer caffeine) { this.caffeine = caffeine; }
    public void setPrice(Double price) { this.price = price; }
}
