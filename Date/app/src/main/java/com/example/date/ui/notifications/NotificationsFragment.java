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


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Log.d("response", ""+response);
                    JSONArray list1 = jsonResponse.getJSONObject(0).getJSONArray("latitudeArray");
                    JSONArray list2 = jsonResponse.getJSONObject(0).getJSONArray("longitudeArray");
                    ArrayList<String> latitudeList = new ArrayList<String>();
                    ArrayList<String> longitudeList = new ArrayList<String>();
                    if (list1 != null) {
                        int len = list1.length();
                        for (int i=0;i<len;i++){
                            latitudeList.add(list1.get(i).toString());
                        }
                    }
                    if (list2 != null) {
                        int len = list2.length();
                        for (int i=0;i<len;i++){
                            longitudeList.add(list2.get(i).toString());
                        }
                    }
                    String city = jsonResponse.getJSONObject(0).getString("city");
                    String purpose = jsonResponse.getJSONObject(0).getString("purpose");
                    String level = jsonResponse.getJSONObject(0).getString("level");
                    Integer courseLevel = 0;
                    if(level.equals("1")) courseLevel = 1;
                    else if(level.equals("2")) courseLevel = 2;
                    else if(level.equals("3")) courseLevel = 3;
                    CourseInformation courseInformation = new CourseInformation();
                    courseInformation.setCity(city);
                    courseInformation.setPurpose(purpose);
                    courseInformation.setLevel(courseLevel);
                    courseInformation.setLatitudeList(latitudeList);
                    courseInformation.setLongitudeList(longitudeList);
                    Log.d("result", ""+latitudeList + longitudeList);
                    Intent intent = new Intent(getContext(), CourseActivity.class);
                    intent.putExtra("courseInformation", courseInformation);
//                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        CourseRequest courseRequest = new CourseRequest("seoul", "3", "rest", responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(courseRequest);


        return v;
    }



}