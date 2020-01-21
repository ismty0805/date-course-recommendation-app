package com.example.date.ui.account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.date.MainActivity;
import com.example.date.R;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonResponse = new JSONArray(response);

                            int i = 0;
                            while (i < jsonResponse.length()) {
                                final String right_userID = jsonResponse.getJSONObject(i).getString("userID");
                                String right_userPassword = jsonResponse.getJSONObject(i).getString("userPassword");

                                if (right_userID.equals(userID) && right_userPassword.equals(userPassword)) {
                                    SaveSharedPreference.setUserID(LoginActivity.this, userID);
                                    String level = jsonResponse.getJSONObject(i).getString("level");
//                                    String userImg = jsonResponse.getJSONObject(i).getString("userImg");
                                    String name = jsonResponse.getJSONObject(i).getString("name");
                                    String email = jsonResponse.getJSONObject(i).getString("email");
                                    String city = jsonResponse.getJSONObject(i).getString("location");
                                    SaveSharedPreference.setLevel(LoginActivity.this, level);
                                    SaveSharedPreference.setCity(LoginActivity.this, city);
                                    SaveSharedPreference.setName(LoginActivity.this, name);
                                    SaveSharedPreference.setEmail(LoginActivity.this, email);
//                                    SaveSharedPreference.setImg(LoginActivity.this, userImg);
                                    login = true;
                                    Toast.makeText(LoginActivity.this, "로그인에 성공했습니다", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    //Intent intent1 = new Intent(LoginActivity.this, LockScreenActivity.class);
                                    intent.putExtra("userID", userID);
                                    //intent1.putExtra("userID", userID);
                                    LoginActivity.this.startActivity(intent);
                                    finish();
                                    break;
                                }
                                else {i++;}
                            }
                            if (!login) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요")
                                        .setNegativeButton("다시시도", noButtonClickListener)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    private DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
        }
    };

    private DialogInterface.OnClickListener noButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
