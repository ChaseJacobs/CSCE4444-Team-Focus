package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/16/2016.
 */

public class DeleteWorkoutRequest extends StringRequest {
private static final String NEW_WORKOUT_REQUEST_URL = ServerConstants.CLOUD + "DeleteWorkout.php";
    private Map<String, String> params;

    public DeleteWorkoutRequest(String username, String name, String exercise1, String time1, String exercise2, String time2, String exercise3, String time3,
                                String exercise4, String time4, String exercise5, String time5, String exercise6, String time6, Response.Listener listener){
        super(Method.POST, NEW_WORKOUT_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("username", username);
        params.put("name", name);
        params.put("exercise1", exercise1);
        params.put("exercise2", exercise2);
        params.put("exercise3", exercise3);
        params.put("exercise4", exercise4);
        params.put("exercise5", exercise5);
        params.put("exercise6", exercise6);
        params.put("time1", time1);
        params.put("time2", time2);
        params.put("time3", time3);
        params.put("time4", time4);
        params.put("time5", time5);
        params.put("time6", time6);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
