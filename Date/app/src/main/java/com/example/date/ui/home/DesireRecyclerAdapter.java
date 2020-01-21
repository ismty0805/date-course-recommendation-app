package com.example.date.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.date.R;
import com.example.date.ui.account.SaveSharedPreference;
import com.example.date.ui.home.Course.CourseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DesireRecyclerAdapter extends RecyclerView.Adapter<DesireRecyclerAdapter.DesireHolder> {

    private ArrayList<String> desires;
    private LayoutInflater mInflater;
    private Context mContext;
    private String level;
    private String city;

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
        switch (text) {
            case "갈등":
                holder.desireImage.setImageResource(R.drawable.piece);
                break;
            case "진도":
                holder.desireImage.setImageResource(R.drawable.jindo);
                break;
            case "휴식":
                holder.desireImage.setImageResource(R.drawable.chill);
                break;
            case "공식":
                holder.desireImage.setImageResource(R.drawable.formula);
                break;
            case "여행":
                holder.desireImage.setImageResource(R.drawable.travel);
                break;
            case "이별":
                holder.desireImage.setImageResource(R.drawable.breakup);
                break;
        }
        level = SaveSharedPreference.getLevel(mContext);
        city = SaveSharedPreference.getCity(mContext);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonResponse = new JSONArray(response);
                            CourseInformation courseInformation = new CourseInformation();
                            setCourseInfoWithJson(jsonResponse, courseInformation);

                            Intent intent = new Intent(mContext, CourseActivity.class);
                            intent.putExtra("courseInformation", courseInformation);
                            mContext.startActivity(intent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                };
                CourseRequest courseRequest = new CourseRequest(city, level, text, responseListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(courseRequest);
            }
        });
    }

    public class DesireHolder extends RecyclerView.ViewHolder {

        private ImageView desireImage;

        public DesireHolder(View itemView) {
            super(itemView);
            desireImage = (ImageView) itemView.findViewById(R.id.desireItemImage);
        }
    }

    /*---------------------------------------- helpers -------------------------------------------*/
    public void setCourseInfoWithJson(JSONArray jsonArray, CourseInformation courseInformation) {
        try {
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                JSONArray latList = jsonObject.getJSONArray("latitudeArray");
//                JSONArray lngList = jsonObject.getJSONArray("longitudeArray");
                JSONArray placeList = jsonObject.getJSONArray("placeArray");
                JSONArray commentList = jsonObject.getJSONArray("commentArray");

//                ArrayList<String> latitudes = new ArrayList<>();
//                ArrayList<String> longitudes = new ArrayList<>();
                ArrayList<String> places = new ArrayList<>();
                ArrayList<String> comments = new ArrayList<>();

//                if (latList != null) {
//                    int len = latList.length();
//                    for (int j=0;j<len;j++){
//                        latitudes.add(latList.get(j).toString());
//                    }
//                }
//                if (lngList != null) {
//                    int len = lngList.length();
//                    for (int k=0;k<len;k++){
//                        longitudes.add(lngList.get(k).toString());
//                    }
//                }
                if (placeList != null) {
                    int len = placeList.length();
                    for (int l=0;l<len;l++){
                        places.add(placeList.get(l).toString());
                    }
                }
                if (commentList != null) {
                    int len = commentList.length();
                    for (int l=0;l<len;l++){
                        comments.add(commentList.get(l).toString());
                    }
                }

                String city = jsonObject.getString("city");
                String purpose = jsonObject.getString("purpose");
                int level = Integer.valueOf(jsonObject.getString("level"));

                courseInformation.setCity(city);
                courseInformation.setPurpose(purpose);
                courseInformation.setLevel(level);
                courseInformation.setPlaceList(places);
                courseInformation.setCommentList(comments);
//                courseInformation.setLatitudeList(latitudes);
//                courseInformation.setLongitudeList(longitudes);
//                courseInformation.setLongitudeList(places);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
