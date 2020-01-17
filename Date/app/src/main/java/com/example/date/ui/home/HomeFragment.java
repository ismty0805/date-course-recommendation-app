package com.example.date.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.date.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    // temporal recyclerview input
    private ArrayList<String> desires;
    private String[] arrDesire = {"a", "b", "c", "d", "e", "f"};

    private LayoutInflater inflater;
    private GridLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Spinner spinner = (Spinner)v.findViewById(R.id.timeSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //10시
                    case 1:
                        //11시
                    case 2:
                        //12시
                    case 3:
                        //13시
                    case 4:
                        //14시
                    case 5:
                        //15시
                    case 6:
                        //16시
                    case 7:
                        //17시
                    case 8:
                        //18시
                    case 9:
                        //19시
                    case 10:
                        //20시
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView desireRecyclerView = getActivity().findViewById(R.id.desireRecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        return v;
    }
}