package com.example.date.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.date.R;
import com.example.date.ui.home.Course.CourseActivity;

import java.util.ArrayList;

public class DesireRecyclerAdapter extends RecyclerView.Adapter<DesireRecyclerAdapter.DesireHolder> {

    private ArrayList<String> desires;
    private LayoutInflater mInflater;
    private Context mContext;

    public DesireRecyclerAdapter(ArrayList<String> desires, LayoutInflater inflater, Context context) {
        this.desires = desires;
        this.mInflater = inflater;
        this.mContext = context;
    }

    @Override
    public int getItemCount() { return desires.size(); }
    public String getItem(int i) { return desires.get(i); }

    @NonNull
    @Override
    public DesireHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_desire, parent, false);
        Log.d("onCreateViewHolder", "logged");
        return new DesireHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesireHolder holder, final int position) {

        final String text = desires.get(position);
        holder.desireText.setText(text);
        holder.desireImage.setImageResource(R.mipmap.ic_launcher);
        Log.d("Adapter", text);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseActivity.class);

                // passing desire information
                intent.putExtra("desire", text);
                mContext.startActivity(intent);
            }
        });
    }

    public class DesireHolder extends RecyclerView.ViewHolder {

        private TextView desireText;
        private ImageView desireImage;

        public DesireHolder(View itemView) {
            super(itemView);
            desireImage = (ImageView) itemView.findViewById(R.id.desireItemImage);
            desireText = (TextView) itemView.findViewById(R.id.desireItemText);
        }
    }
}
