package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/17/2016.
 */

public class GetWorkoutListRequest extends StringRequest{
    private static final String GET_WORKOUT_LIST_REQUEST_URL = ServerConstants.CLOUD + "GetWorkoutList.php";
    private Map<String, String> params;

    public GetWorkoutListRequest(String username, String name, Response.Listener listener){
        super(Method.POST, GET_WORKOUT_LIST_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
