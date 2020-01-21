package com.example.date.ui.home.Course;

import android.Manifest;
import android.os.Bundle;

import com.example.date.R;
import com.example.date.ui.home.CourseInformation;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private ArrayList<View> views;
    private CoursePagerAdapter coursePagerAdapter;

    private ArrayList<Place> spots;
    private ArrayList<String> comments;
    public static CourseInformation courseInformation;
    private String apiKey = "AIzaSyCWL29hG8BAnMUbNN6HRt3wgUm3JBpCJ_s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseInformation = (CourseInformation) getIntent().getSerializableExtra("courseInformation");

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        views = new ArrayList<>();

        /*---------------------- setting spot list using PlaceAPI ---------------------------*/
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.PHONE_NUMBER,
                Place.Field.PHOTO_METADATAS,
                Place.Field.ADDRESS
                );

        ArrayList<String> placeList = courseInformation.getPlaceList();
        int size = placeList.size();
        Log.d("SIZE", size+"");
        spots = new ArrayList<>();

        if (size==0) {
            coursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager());
            coursePagerAdapter.setSpots(spots);
            coursePagerAdapter.setType(courseInformation.getPurpose());
            coursePagerAdapter.setComments(courseInformation.getCommentList());
            coursePagerAdapter.setContext(this);
            viewPager.setAdapter(coursePagerAdapter);
            tabs.setupWithViewPager(viewPager);
        }
        for (int i=0; i<size; i++) {
            spots.add(null);
            String placeID = placeList.get(i);
            FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeID, placeFields);

            int finalI = i;
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                Log.d("Place", "Name: "+place.getName());
                spots.set(finalI, place);

                if (!spots.contains(null)) {
                    coursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager());
                    coursePagerAdapter.setSpots(spots);
                    coursePagerAdapter.setType(courseInformation.getPurpose());
                    coursePagerAdapter.setComments(courseInformation.getCommentList());
                    viewPager.setAdapter(coursePagerAdapter);
                    tabs.setupWithViewPager(viewPager);
                }

            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    Log.e("Exception", "Place not found: "+exception.getMessage());
                }
            });
        }
    }
}