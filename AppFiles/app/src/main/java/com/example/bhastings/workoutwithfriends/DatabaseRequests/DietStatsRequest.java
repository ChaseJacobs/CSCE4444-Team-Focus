package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/14/2016.
 */

public class DietStatsRequest extends StringRequest {
    private static final String DIET_STATS_REQUEST = ServerConstants.CLOUD + "DietStats.php";
    private Map<String, String> params;

    public DietStatsRequest(String username, Response.Listener<String> listener){
        super(Method.POST, DIET_STATS_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
