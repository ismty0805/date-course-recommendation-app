package com.example.date.ui.home.Course;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.date.R;


public class CourseStartFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView imageView;
    private LinearLayout background;
    private String type;

    public static CourseStartFragment newInstance(int index, String type) {
        CourseStartFragment fragment = new CourseStartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        fragment.setType(type);
        return fragment;
    }

    public CourseStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_start, container, false);

        imageView = (ImageView) root.findViewById(R.id.courseStartImage);
        background = (LinearLayout) root.findViewById(R.id.courseStartBackground);

        switch (type) {
            case "갈등":
                imageView.setImageResource(R.drawable.piece_start);
                background.setBackgroundResource(R.drawable.flower1);
                break;
            case "진도":
                imageView.setImageResource(R.drawable.jindo_start);
                background.setBackgroundResource(R.drawable.flower4);
                break;
            case "휴식":
                imageView.setImageResource(R.drawable.chill_start);
                background.setBackgroundResource(R.drawable.flower3);
                break;
            case "공식":
                imageView.setImageResource(R.drawable.formula_start);
                background.setBackgroundResource(R.drawable.flower2);
                break;
            case "여행":
                imageView.setImageResource(R.drawable.travel_start);
                background.setBackgroundResource(R.drawable.flower6);
                break;
            case "이별":
                imageView.setImageResource(R.drawable.breakup_start);
                background.setBackgroundResource(R.drawable.flower5);
                break;
        }

        return root;
    }

    public void setType(String type) {
        this.type = type;
    }
}
