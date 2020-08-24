package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import android.view.View;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/13/2016.
 */

public class WorkoutStatsRequest extends StringRequest {
    private static final String WORKOUT_STATS_REQUEST_URL = ServerConstants.CLOUD + "WorkoutStats.php";
    private Map<String, String> params;

    public WorkoutStatsRequest(String username, Response.Listener listener){
        super(Method.POST, WORKOUT_STATS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
