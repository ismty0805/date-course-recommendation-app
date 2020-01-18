package com.example.date.ui.mypage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.date.R;

public class CustomDialog extends Dialog {

    private ImageButton seoul;
    private ImageButton jeju;
    private ImageButton daejeon;
    private ImageButton busan;

    private View.OnClickListener seoulListener;
    private View.OnClickListener jejuListener;
    private View.OnClickListener daejeonListener;
    private View.OnClickListener busanListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog);

        //셋팅
        seoul = findViewById(R.id.seoul);
        jeju = findViewById(R.id.jeju);
        daejeon = findViewById(R.id.daejeon);
        busan = findViewById(R.id.busan);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        seoul.setOnClickListener(seoulListener);
        jeju.setOnClickListener(jejuListener);
        daejeon.setOnClickListener(daejeonListener);
        busan.setOnClickListener(busanListener);
    }

    //생성자 생성
    public CustomDialog(@NonNull Context context, View.OnClickListener seoulListener, View.OnClickListener jejuListener, View.OnClickListener daejeonListener, View.OnClickListener busanListener) {
        super(context);
        this.seoulListener = seoulListener;
        this.jejuListener = jejuListener;
        this.daejeonListener = daejeonListener;
        this.busanListener = busanListener;
    }
}