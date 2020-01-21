package com.example.date.ui.home.Course;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.date.R;

public class DoItYourselfFragment extends Fragment {


    public DoItYourselfFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_do_it_yourself, container, false);

        return root;
    }

}
