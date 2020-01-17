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
    private float totalSpan = 1500;
    private float redSpan = 500;
    private float yellowSpan = 500;
    private float greenSpan = 500;
    private int level;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        Intent intent = getActivity().getIntent();
        final ImageView imageView = v.findViewById(R.id.imageView);
        final TextView nameText = v.findViewById(R.id.name);
        final TextView emailText = v.findViewById(R.id.email);
        String userId = intent.getStringExtra("userID");
        LinearLayout profileLayout = v.findViewById(R.id.profileLayout);
        Log.d("width", ""+profileLayout.getWidth());
        ViewGroup.LayoutParams params = profileLayout.getLayoutParams();
        params.height = profileLayout.getWidth();
        profileLayout.setLayoutParams(params);
        seekBar = ((CustomSeekBar) v.findViewById(R.id.customSeekBar));
        initDataToSeekbar();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int startProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<33){
                    level = 1;
                    Toast.makeText(container.getContext(),"연애 단계 1단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                }
                else if(progress<66){
                    level = 2;
                    Toast.makeText(container.getContext(),"연애 단계 2단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    level = 3;
                    Toast.makeText(container.getContext(),"연애 단계 3단계로 설정되었습니다", Toast.LENGTH_SHORT).show();
                }
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
                    imageView.setImageBitmap(bitmap);
                    nameText.setText(name);
                    emailText.setText(email);
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