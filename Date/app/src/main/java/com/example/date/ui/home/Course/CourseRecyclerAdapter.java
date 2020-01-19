package com.example.date.ui.home.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.date.R;
import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.SpotHolder> {
    private ArrayList<Place> spots = new ArrayList<>();
    private Context context;

    CourseRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SpotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spot, parent, false);
        return new SpotHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotHolder holder, int position) {
        final Place spot = spots.get(position);
        holder.spotNameText.setText(spot.getName());
    }

    @Override
    public int getItemCount() {
        return spots.size();
    }

    public void setSpots(ArrayList<Place> spots) {
        this.spots = spots;
        notifyDataSetChanged();
    }

    class SpotHolder extends RecyclerView.ViewHolder {
        private TextView spotNameText;

        public SpotHolder(View itemView) {
            super(itemView);
            spotNameText = itemView.findViewById(R.id.itemSpotName);
        }
    }
}
