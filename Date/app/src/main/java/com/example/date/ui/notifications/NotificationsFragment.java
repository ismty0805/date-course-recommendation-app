package com.example.date.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.example.date.ui.home.Course.CourseActivity;
import com.example.date.ui.home.CourseInformation;
import com.example.date.ui.mypage.PersonalInfoRequest;
// import com.google.android.gms.location.places.GeoDataApi;
// import com.google.android.gms.location.places.Place;
// import com.google.android.gms.location.places.Places;

import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        final Button logoutButton = v.findViewById(R.id.logOut);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SaveSharedPreference.clearUserName(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });





        return v;
    }



}