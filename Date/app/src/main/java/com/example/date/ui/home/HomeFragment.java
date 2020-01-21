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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.date.R;
import com.example.date.ui.account.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    // temporal recyclerview input
    private String[] arrDesire = {"갈등", "진도", "휴식", "공식", "여행", "이별"};
    private ArrayList<String> desires = new ArrayList(Arrays.asList(arrDesire));
    private LayoutInflater inflater;
    private LinearLayoutManager layoutManager;
    private DesireRecyclerAdapter adapter;
    private RecyclerView desireRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        desireRecyclerView = root.findViewById(R.id.desireRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new DesireRecyclerAdapter(desires, inflater, getActivity());
        desireRecyclerView.setAdapter(adapter);
        desireRecyclerView.setLayoutManager(layoutManager);


        return root;
    }
}