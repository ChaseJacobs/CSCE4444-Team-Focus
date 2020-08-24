package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/13/2016.
 */

public class CaloriesBurnedRequest extends StringRequest {
    private static final String WORKOUT_STATS_REQUEST_URL = ServerConstants.CLOUD + "GetCalories.php";
    private Map<String, String> params;

    public CaloriesBurnedRequest(String username, String name, Response.Listener listener){
        super(Method.POST, WORKOUT_STATS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
