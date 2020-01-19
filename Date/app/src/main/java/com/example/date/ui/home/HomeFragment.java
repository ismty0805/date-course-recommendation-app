package com.example.date.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
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
import com.example.date.ui.account.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    // temporal recyclerview input
    private String[] arrDesire = {"갈등", "진도", "휴식", "액티비티", "공식", "여행", "이별"};
    private ArrayList<String> desires = new ArrayList(Arrays.asList(arrDesire));
    private LayoutInflater inflater;
    private GridLayoutManager layoutManager;
    private DesireRecyclerAdapter adapter;
    private RecyclerView desireRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Spinner spinner = (Spinner) root.findViewById(R.id.timeSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        desireRecyclerView = root.findViewById(R.id.desireRecyclerView);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new DesireRecyclerAdapter(desires, inflater, getActivity());
        desireRecyclerView.setAdapter(adapter);
        desireRecyclerView.setLayoutManager(layoutManager);


        return root;
    }
}