package com.example.date.ui.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeAreaRequest extends StringRequest {

    final static private String URL = "http://192.249.19.252:2080/changeArea";
    private Map<String, String> parameters;

    public ChangeAreaRequest(String userID, String location, Response.Listener<String> listener) {
        super(Method.PUT, URL + "/" + userID, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("location", location);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}