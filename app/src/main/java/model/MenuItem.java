package model;

public class MenuItem {

    private String desctiption;
    private Double price;
    private String size;
    //private String nutrition;
    private String caffeine;
    //I added a flavor string bc I think it might be useful for recommendations
    //ex) allows us to recommend a latte and say maybe try a new flavor
    private String flavor;

    //getter functions
    public String getDesctiption() { return desctiption; }
    public Double getPrice() { return price; }
    public String getSize() { return size; }
   // public String getNutrition() { return nutrition; }
    public String getCaffeine() { return caffeine; }
    public String getFlavor() { return flavor; }

    //setter/change functions
    public void setDesctiption(String desctiption) { this.desctiption = desctiption; }
    public void setPrice(Double price) { this.price = price; }
    public void setSize(String size) { this.size = size; }
    //public void setNutrition(String nutrition) { this.nutrition = nutrition; }
    public void setCaffeine(String caffeine) { this.caffeine = caffeine; }
    public void setFlavor(String flavor) { this.flavor = flavor; }
}
