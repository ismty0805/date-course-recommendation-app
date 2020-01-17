package com.example.date.ui.home.Course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.date.R;

public class CourseEndFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static CourseStartFragment newInstance(int index) {
        CourseStartFragment fragment = new CourseStartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CourseEndFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_end, container, false);

        TextView textView = (TextView) getActivity().findViewById(R.id.courseTip);
        textView.setText("course tip");
        return textView;
    }
}

