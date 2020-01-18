package com.example.date.ui.notifications;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CourseRequest extends StringRequest {

    final static private String URL = "http://192.249.19.252:2080/courses";
    private Map<String, String> parameters;

    public CourseRequest(String city, String level, String purpose, Response.Listener<String> listener) {
        super(Method.PUT, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("city", city);
        parameters.put("level", level);
        parameters.put("purpose", purpose);
        Log.d("request", ""+parameters);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}