package com.sbo.maps;

public class TripsTakenByUser {
    private String location_coordinates;

    public TripsTakenByUser(String location_coordinates) {
        this.location_coordinates = location_coordinates;
    }
    public TripsTakenByUser(){

    }
    public String getLocation_coordinates() {
        return location_coordinates;
    }

    public void setLocation_coordinates(String location_coordinates) {
        this.location_coordinates = location_coordinates;
    }
}
