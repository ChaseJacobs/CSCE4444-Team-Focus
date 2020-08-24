package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/16/2016.
 */

public class EditWorkoutRequest extends StringRequest {
    private static final String EDIT_WORKOUT_REQEUST_URL = ServerConstants.CLOUD + "EditWorkout.php";
    private Map<String, String> params;

    public EditWorkoutRequest(String username, String name, String newname, String exercise1, String exercise2, String exercise3, String exercise4, String exercise5, String exercise6,
                              String time1, String time2, String time3, String time4, String time5, String time6, Response.Listener listener){
        super(Method.POST, EDIT_WORKOUT_REQEUST_URL, listener, null);
        params = new HashMap<>();

        params.put("username", username);
        params.put("name", name);
        params.put("newname", newname);
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
