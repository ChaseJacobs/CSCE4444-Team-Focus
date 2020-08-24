package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/14/2016.
 */

public class UpdateBestRequest extends StringRequest {
    private static final String RECORD_MEAL_REQUEST = ServerConstants.CLOUD + "UpdateBest.php";
    private Map<String, String> params;

    public UpdateBestRequest(String username, String bestdist, String besttm, Response.Listener<String> listener){
        super(Method.POST, RECORD_MEAL_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("bestdist", bestdist);
        params.put("besttm", besttm);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
