package model;

import android.view.Menu;

public class MenuItem {

    private String name;
    private String flavor;
    private Integer calories;
    private boolean hasSmall;
    private boolean hasMedium;
    private boolean hasLarge;
    private Integer caffeineSmall;
    private Integer caffeineMedium;
    private Integer caffeineLarge;
    private Double priceSmall;
    private Double priceMedium;
    private Double priceLarge;

    public MenuItem(String name, Integer calories) {
        setName(name);
        setCalories(calories);
        this.hasSmall = false;
        this.hasMedium = false;
        this.hasLarge = false;
    }

    public MenuItem(String name, String flavor, Integer calories) {
        setName(name);
        setFlavor(flavor);
        setCalories(calories);
        this.hasSmall = false;
        this.hasMedium = false;
        this.hasLarge = false;
    }

    public void addSmallSize(Integer caffeine, Double price) {
        caffeineSmall = caffeine;
        priceSmall = price;
        this.hasSmall = true;
    }

    public void removeSmall() {
        caffeineSmall = null;
        priceSmall = null;
        this.hasSmall = false;
    }

    public void addMediumSize(Integer caffeine, Double price) {
        caffeineMedium = caffeine;
        priceMedium = price;
        this.hasMedium = true;
    }

    public void removeMedium() {
        caffeineMedium = null;
        priceMedium = null;
        this.hasMedium = false;
    }

    public void addLargeSize(Integer caffeine, Double price) {
        caffeineLarge = caffeine;
        priceLarge = price;
        this.hasLarge = true;
    }

    public void removeLarge() {
        caffeineLarge = null;
        priceLarge = null;
        this.hasLarge = false;
    }

    //getter functions
    public String getName() { return name; }
    public String getFlavor() { return flavor; }
    public Integer getCalories() { return calories; }


    //setter/change functions
    public void setName(String name) { this.name = name; }
    public void setFlavor(String flavor) { this.flavor = flavor; }
    public void setCalories(Integer calories) { this.calories = calories; }
}
