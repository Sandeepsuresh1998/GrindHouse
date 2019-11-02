package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Trip {

    private int travelTime;
    private Double mileage;
    private LatLng startingPoint;
    private LatLng endingPoint;
    private Order order;

    //getter functions
    public int getTravelTime() { return travelTime; }
    public Double getMileage() { return mileage; }
    public LatLng getStartingPoint() { return startingPoint; }
    public LatLng getEndingPoint() { return endingPoint; }
    public Order getOrder() { return order; }

    //setter functions
    public void setTravelTime(int travelTime) { this.travelTime = travelTime; }
    public void setMileage(Double mileage) { this.mileage = mileage; }
    public void setStartingPoint(LatLng startingPoint) { this.startingPoint = startingPoint; }
    public void setEndingPoint(LatLng endingPoint) { this.endingPoint = endingPoint; }
    public void setOrder(Order order) { this.order = order; }

    //calculate the travel time of the trip
    public int calculateTravelTime(){
        int tr = 0;
        return tr;
    }

    //create a set of directions for the user using the Google Maps API
    public ArrayList<String> generateDirections(){
        ArrayList<String> directions = new ArrayList<>();
        return directions;
    }
}
