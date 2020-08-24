package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjacobs on 12/2/2016.
 */

public class CaloriesBurnedRequest2 extends StringRequest {
    private static final String WORKOUT_STATS_REQUEST_URL = ServerConstants.CLOUD + "GetCalories2.php";
    private Map<String, String> params;

    public CaloriesBurnedRequest2(String username, Response.Listener listener){
        super(Method.POST, WORKOUT_STATS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
