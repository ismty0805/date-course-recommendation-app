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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.date.R;
import com.example.date.ui.home.Course.CourseActivity;
import com.example.date.ui.notifications.CourseRequest;

import org.json.JSONArray;

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
        return new DesireHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesireHolder holder, final int position) {

        final String text = desires.get(position);
        holder.desireText.setText(text);
        holder.desireImage.setImageResource(R.mipmap.ic_launcher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Intent intent = new Intent(mContext, CourseActivity.class);
                            intent.putExtra("courseInformation", courseInformation);
                            mContext.startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                };
                CourseRequest courseRequest = new CourseRequest("seoul", "3", "rest", responseListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(courseRequest);
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
