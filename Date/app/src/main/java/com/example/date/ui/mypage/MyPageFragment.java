package com.example.date.ui.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.date.MainActivity;
import com.example.date.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {


    private CustomSeekBar seekBar;
    private ArrayList<ProgressItem> progressItemArrayList;
    private ProgressItem mProgressItem;
    private Button locationBtn;
    private float totalSpan = 1500;
    private float redSpan = 500;
    private float yellowSpan = 500;
    private float greenSpan = 500;
    private String level;
    private String userId;
    private CustomDialog customDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        Intent intent = getActivity().getIntent();
        final ImageView imageView = v.findViewById(R.id.imageView);
        final TextView nameText = v.findViewById(R.id.name);
        final TextView emailText = v.findViewById(R.id.email);
        locationBtn = v.findViewById(R.id.locationBtn);
        userId = intent.getStringExtra("userID");
        seekBar = ((CustomSeekBar) v.findViewById(R.id.customSeekBar));
        initDataToSeekbar();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int startProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startProgress = seekBar.getProgress();
            }
            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                if((seekBar.getProgress()>66) && startProgress<=66){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("경고").setMessage("3단계 설정시 높은 수위의 코스가 추천 될 수 있습니다. 괜찮으시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            level = "3";
                            Toast.makeText(container.getContext(),"연애 단계 3단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            ChangeLevelRequest changeLevelRequest = new ChangeLevelRequest(userId, level, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            queue.add(changeLevelRequest);
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(container.getContext(),"취소되었습니다", Toast.LENGTH_SHORT);
                            seekBar.setProgress(startProgress);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if(seekBar.getProgress()<33 && startProgress>=33){
                    level = "1";
                    Toast.makeText(container.getContext(),"연애 단계 1단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    ChangeLevelRequest changeLevelRequest = new ChangeLevelRequest(userId, level, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(changeLevelRequest);
                }
                else if ((seekBar.getProgress()<66)&&(seekBar.getProgress()>=33)&&((startProgress>66)||(startProgress<33))){
                    level = "2";
                    Toast.makeText(container.getContext(),"연애 단계 2단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    ChangeLevelRequest changeLevelRequest = new ChangeLevelRequest(userId, level, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(changeLevelRequest);
                }
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new CustomDialog(container.getContext(), seoulListener, jejuListener, daejeonListener, busanListener);
                customDialog.show();
            }
        });




        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    String userImg = jsonResponse.getJSONObject(0).getString("userImg");
                    Bitmap bitmap = getBitmapFromString(userImg);
                    String name = jsonResponse.getJSONObject(0).getString("name");
                    String email = jsonResponse.getJSONObject(0).getString("email");
                    String location = jsonResponse.getJSONObject(0).getString("location");
                    String level = jsonResponse.getJSONObject(0).getString("level");
                    imageView.setImageBitmap(bitmap);
                    nameText.setText(name);
                    emailText.setText(email);
                    if(level.equals("1")) {
                        seekBar.setProgress(16);
                    }
                    if(level.equals("2")) {
                        seekBar.setProgress(49);
                    }
                    if(level.equals("3")) {
                        seekBar.setProgress(82);
                    }
                    locationBtn.setText("관심지역: "+location);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(personalInfoRequest);



        return v;
    }

    private View.OnClickListener seoulListener = new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getContext(), "관심지역이 서울로 설정되었습니다", Toast.LENGTH_SHORT).show();
            locationBtn.setText("관심지역: "+"서울");
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            };
            ChangeAreaRequest changeAreaRequest = new ChangeAreaRequest(userId, "서울", responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(changeAreaRequest);
            customDialog.dismiss();
        }
    };
    private View.OnClickListener jejuListener = new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getContext(), "관심지역이 제주로 설정되었습니다", Toast.LENGTH_SHORT).show();
            locationBtn.setText("관심지역: "+"제주");
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            };
            ChangeAreaRequest changeAreaRequest = new ChangeAreaRequest(userId, "제주", responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(changeAreaRequest);
            customDialog.dismiss();
        }
    };
    private View.OnClickListener daejeonListener = new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getContext(), "관심지역이 대전으로 설정되었습니다", Toast.LENGTH_SHORT).show();
            locationBtn.setText("관심지역: "+"대전");
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            };
            ChangeAreaRequest changeAreaRequest = new ChangeAreaRequest(userId, "대전", responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(changeAreaRequest);
            customDialog.dismiss();
        }
    };
    private View.OnClickListener busanListener = new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getContext(), "관심지역이 부산으로 설정되었습니다", Toast.LENGTH_SHORT).show();
            locationBtn.setText("관심지역: "+"부산");
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            };
            ChangeAreaRequest changeAreaRequest = new ChangeAreaRequest(userId, "부산", responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(changeAreaRequest);
            customDialog.dismiss();
        }
    };


    private Bitmap getBitmapFromString(String string){
        String[] bytevalues = string.substring(1, string.length() -1).split(",");
        byte[] bytes = new byte[bytevalues.length];
        for(int j=0, len=bytes.length; j<len; j++){
            bytes[j] = Byte.parseByte(bytevalues[j].trim());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
    private void initDataToSeekbar(){
        progressItemArrayList = new ArrayList<ProgressItem>();
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((greenSpan/totalSpan)*100);
        mProgressItem.color = R.color.colorGreen;
        progressItemArrayList.add(mProgressItem);
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((yellowSpan/totalSpan)*100);
        mProgressItem.color = R.color.colorYellow;
        progressItemArrayList.add(mProgressItem);
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((redSpan/totalSpan)*100);
        mProgressItem.color = R.color.colorRed;
        progressItemArrayList.add(mProgressItem);

        seekBar.initData(progressItemArrayList);
        seekBar.invalidate();
    }
}