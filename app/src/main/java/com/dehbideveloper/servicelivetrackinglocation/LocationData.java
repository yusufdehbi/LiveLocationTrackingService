package com.dehbideveloper.servicelivetrackinglocation;

public class LocationData {

    private int id;
    private double latitude;
    private double longitude;

    public LocationData(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationData(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationData : " +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude;
    }
}
