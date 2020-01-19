package com.example.date.ui.home.Course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.date.R;
import com.google.android.libraries.places.api.model.Place;

public class CourseDetailFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView textView;
    private Place spot;

    public static CourseDetailFragment newInstance(int index, Place spot) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        fragment.setSpot(spot);
        return fragment;
    }

    public CourseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_detail, container, false);

        textView = (TextView) root.findViewById(R.id.spotName);
        textView.setText("spot name");
        return root;
    }

    public void setSpot(Place spot) {
        this.spot = spot;
    }
}

