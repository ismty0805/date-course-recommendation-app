package com.example.date.ui.mypage;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeLevelRequest extends StringRequest {

    final static private String URL = "http://192.249.19.252:2080/changeLevel";
    private Map<String, String> parameters;

    public ChangeLevelRequest(String userID, String level, Response.Listener<String> listener) {
        super(Method.PUT, URL + "/" + userID, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("level", level);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}