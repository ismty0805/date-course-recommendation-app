package com.example.date.ui.home;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CourseInformation implements Serializable {
//    private ArrayList<String> latitudeList = new ArrayList<>();
//    private ArrayList<String> longitudeList = new ArrayList<>();
    private ArrayList<String> placeList = new ArrayList<>();
    private ArrayList<String> commentList = new ArrayList<>();
    private int level = 1;
    private String city = "";
    private String purpose = "";

    public CourseInformation() {}

    // setters
//    public void setLatitudeList(ArrayList<String> latitudeList) {
//        this.latitudeList = latitudeList;
//    }
//    public void setLongitudeList(ArrayList<String> longitudeList) {
//        this.longitudeList = longitudeList;
//    }
    public void setPlaceList(ArrayList<String> placeList) {
        this.placeList = placeList;
    }
    public void setCommentList(ArrayList<String> commentList) {
        this.commentList = commentList;
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
//    public ArrayList<String> getLatitudeList() {
//        return latitudeList;
//    }
//    public ArrayList<String> getLongitudeList() {
//        return longitudeList;
//    }
    public ArrayList<String> getPlaceList() {
        return placeList;
    }
    public ArrayList<String> getCommentList() {
        return commentList;
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
