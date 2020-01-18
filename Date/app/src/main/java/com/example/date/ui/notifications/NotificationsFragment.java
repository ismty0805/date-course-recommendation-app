package com.example.date.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.date.ui.account.LoginActivity;
import com.example.date.R;
import com.example.date.ui.account.SaveSharedPreference;
import com.example.date.ui.mypage.PersonalInfoRequest;

import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        Button logoutButton = v.findViewById(R.id.logOut);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SaveSharedPreference.clearUserName(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    ArrayList<String> latitudeArray = (ArrayList<String>) jsonResponse.getJSONObject(0).get("latitudeArray");
                    ArrayList<String> longitudeArray = (ArrayList<String>) jsonResponse.getJSONObject(0).get("longitudeArray");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        CourseRequest courseRequest = new CourseRequest("대전", "3", "CONFLICT", responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(courseRequest);


        return v;
    }



}