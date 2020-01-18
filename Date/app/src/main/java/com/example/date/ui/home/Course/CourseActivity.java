package com.example.date.ui.home.Course;

import android.os.Bundle;

import com.example.date.R;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseActivity extends AppCompatActivity {

    private ArrayList<View> views;
    private CoursePagerAdapter coursePagerAdapter;

    private String[] arrSpots = {"cafe", "bob", "movie"};
    private ArrayList<String> tempSpots = new ArrayList(Arrays.asList(arrSpots));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        views = new ArrayList<>();

        coursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager());
        coursePagerAdapter.setSpots(tempSpots);
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