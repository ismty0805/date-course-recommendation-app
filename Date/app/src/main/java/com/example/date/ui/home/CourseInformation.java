package com.example.date.ui.home;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseInformation implements Serializable {
    private ArrayList<String> latitudeList;
    private ArrayList<String> longitudeList;
    private int level;
    private String city;
    private String purpose;

    // setters
    public void setLatitudeList(ArrayList<String> latitudeList) {
        this.latitudeList = latitudeList;
    }
    public void setLongitudeList(ArrayList<String> longitudeList) {
        this.longitudeList = longitudeList;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    // getters
    public ArrayList<String> getLatitudeList() {
        return latitudeList;
    }
    public ArrayList<String> getLongitudeList() {
        return longitudeList;
    }
    public int getLevel() {
        return level;
    }
    public String getCity() {
        return city;
    }
    public String getPurpose() {
        return purpose;
    }
}
