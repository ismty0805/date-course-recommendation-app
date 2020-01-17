package com.example.date.ui.home.Course;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.date.R;


public class CourseStartFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static CourseStartFragment newInstance(int index) {
        CourseStartFragment fragment = new CourseStartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CourseStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_start, container, false);

        TextView textView = (TextView) getActivity().findViewById(R.id.courseStartText);
        textView.setText("start text");
        return textView;
    }
}
