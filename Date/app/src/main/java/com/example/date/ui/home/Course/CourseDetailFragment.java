package com.example.date.ui.home.Course;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.date.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

public class CourseDetailFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView nameText;
    private TextView commentText;
    private ImageView spotImage;
    private Place spot;
    private String comment;

    public static CourseDetailFragment newInstance(int index, Place spot, String comment) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        fragment.setSpot(spot);
        fragment.setComment(comment);
        return fragment;
    }

    public CourseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_detail, container, false);

        nameText = (TextView) root.findViewById(R.id.spotName);
        commentText = (TextView) root.findViewById(R.id.spotInfo);
        spotImage = (ImageView) root.findViewById(R.id.spotImage);

        nameText.setText(spot.getName());
        commentText.setText(comment);

        PlacesClient placesClient = Places.createClient(getContext());
        PhotoMetadata photoMetadata = spot.getPhotoMetadatas().get(0);
        FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .setMaxHeight(1000)
                .setMaxWidth(1000)
                .build();
        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
            Bitmap bitmap = fetchPhotoResponse.getBitmap();
            spotImage.setImageBitmap(bitmap);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                Log.e("IMAGE LOAD FAILED", "Photo not found"+exception.getMessage());
            }
        });

        return root;
    }

    public void setSpot(Place spot) {
        this.spot = spot;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}

