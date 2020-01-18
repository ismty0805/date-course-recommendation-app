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
import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.location.places.GeoDataApi;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.Places;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

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
//        Places.initialize(getActivity().getApplicationContext(), "AIzaSyBcen1ykX9JdO0-VtCi4DY3zfCZvCIziLA");
//        PlacesClient placesClient = Places.createClient(getActivity());
//        String placeId = "ChIJlTGB-r5LZTURPFr_c6FdOo4";
//
//
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
//
//        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//            Place place = response.getPlace();
//            Log.i(TAG, "Place found: " + place.getName());
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                int statusCode = apiException.getStatusCode();
//                // Handle error with given status code.
//                Log.e(TAG, "Place not found: " + exception.getMessage());
//            }
//        });


        return v;
    }



}