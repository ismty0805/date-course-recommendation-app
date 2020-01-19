package com.example.date.ui.home.Course;

import java.util.ArrayList;

public class Spot {
    private String name = "";
    private String latitude = "";
    private String longitude = "";

    public Spot() {}

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // getters
    public String getName() {
        return name;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
}
