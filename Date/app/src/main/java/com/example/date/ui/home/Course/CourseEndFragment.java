package com.example.date.ui.home.Course;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.date.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CourseEndFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView textView;

    private MapView mapView;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker currentMarker = null;

    private RecyclerView recyclerView;
    private CourseRecyclerAdapter adapter;
    private ArrayList<Place> spots;
    private LinearLayoutManager layoutManager;

    public static CourseEndFragment newInstance(int index, ArrayList<Place> spots) {
        CourseEndFragment fragment = new CourseEndFragment();
        fragment.setSpots(spots);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CourseEndFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course_end, container, false);

        mapView = (MapView) root.findViewById(R.id.map);
        mapView.getMapAsync(this);

        recyclerView = root.findViewById(R.id.courseRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        adapter = new CourseRecyclerAdapter(getContext());
        adapter.setSpots(spots);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        textView = (TextView) root.findViewById(R.id.courseTip);
        textView.setText("course tip");
        return root;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    /*--------------------------------- getter & setter ----------------------------------*/
    public void setSpots(ArrayList<Place> spots) {
        this.spots = spots;
    }

    /*-------------------------------------- map functions ---------------------------------------*/
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        makeMarker();

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

//        mMap.setInfoWindowAdapter(new CustomMarkerInfoWindowView(getContext()));
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Shop shop = (Shop) marker.getTag();
//                marker.hideInfoWindow();
//                // move to shop detail activity
//                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
//                intent.putExtra("shop", shop);
//                startActivity(intent);
//            }
//        });

        mFusedLocationClient.
                getLastLocation().
                addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            setCurrentLocation(location);
                        }
                    }
                });
    }

    private void makeMarker() {
        for (Place spot : spots) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(spot.getLatLng());
            markerOptions.title(spot.getName());

            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.jeju);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(spot);
        }
    }

    public void setCurrentLocation(Location location) {

        if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        String markerSnippet = "위도:" + location.getLatitude()
                + " 경도:" + location.getLongitude();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        //markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        //currentMarker = mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(zoom); //animateCamera XX
        mMap.moveCamera(cameraUpdate);
    }


}

