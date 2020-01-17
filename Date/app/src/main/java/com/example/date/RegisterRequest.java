package com.example.date;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://192.249.19.252:2080/logins";
    private Map<String, String> parameters;

    public RegisterRequest(String userImg, String userID, String userPassword, String name, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userImg", userImg);
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("name", name);
        parameters.put("email", userEmail);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
