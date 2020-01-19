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
    private CourseInformation courseInformation;
    private String apiKey = "AIzaSyCWL29hG8BAnMUbNN6HRt3wgUm3JBpCJ_s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        /*------------ uncomment below if courseInformation is successfully passed -----------*/
//        courseInformation = (CourseInformation) getIntent().getSerializableExtra("courseInformation");


        /*------------ temporal courseInformation setting : delete later ---------------------*/
        ArrayList<String> places = new ArrayList<>();
        places.add("ChIJV8hePn5MZTURC-4taEqIEG4"); // 칸스테이크하우스
        places.add("ChIJsb73koNLZTURMgCTIa_D95A"); // CGV 유성온천점
        places.add("ChIJh4qkaZxLZTUR3jIRP5tHcu4"); // 봉명가든
        courseInformation = new CourseInformation();
        courseInformation.setCity("Daejeon");
        courseInformation.setLevel(3);
        courseInformation.setPlaceList(places);
        courseInformation.setPurpose("Formula");

        /*---------------------- setting spot list using PlaceAPI ---------------------------*/
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        spots = new ArrayList<>();
        ArrayList<String> placeList = courseInformation.getPlaceList();

        for (String placeID : placeList) {
            FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeID, placeFields);

            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                Log.d("Place", "Name: "+place.getName());
                spots.add(place);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    Log.e("Exception", "Place not found: "+exception.getMessage());
                }
            });
        }

        /*---------------------- setting ViewPager ---------------------------*/
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        views = new ArrayList<>();

        coursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager());
        coursePagerAdapter.setSpots(spots);
        viewPager.setAdapter(coursePagerAdapter);

        tabs.setupWithViewPager(viewPager);

//        // adding page
//        views.add(position, viewToBeAdded);
//        pagerAdapter.notifyDataSetChanged();
//
//        // If we need to set last added page as current page
//        viewPager.setCurrentItem(position);

//        // deleting page
//        viewPager.setAdapter(null);
//        views.remove(view);
//        // Adapter needs to be reinitialised with new list of views
//        pagerAdapter = new Pager(cfViews, getApplicationContext());
//        viewPager.setAdapter(pagerAdapter);
//
//        pagerAdapter.notifyDataSetChanged();
//        viewPager.setCurrentItem(position);


//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}