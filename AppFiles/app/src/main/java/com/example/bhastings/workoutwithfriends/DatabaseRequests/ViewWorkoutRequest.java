package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/16/2016.
 */

public class ViewWorkoutRequest extends StringRequest {
    private static  final String VIEW_WORKOUT_REQEUST_URL = ServerConstants.CLOUD + "WorkoutView.php";
    private Map<String, String> params;

    public ViewWorkoutRequest(String username, String name, Response.Listener listener){
        super (Method.POST, VIEW_WORKOUT_REQEUST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
