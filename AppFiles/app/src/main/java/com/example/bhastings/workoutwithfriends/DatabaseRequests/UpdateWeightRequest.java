package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/14/2016.
 */

public class UpdateWeightRequest extends StringRequest {
    private static final String UPDATE_WEIGHT_REQUEST = ServerConstants.CLOUD + "UpdateWeight.php";
    private Map<String, String> params;

    public UpdateWeightRequest(String username, String weight, Response.Listener <String> listener){
        super(Method.POST, UPDATE_WEIGHT_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("weight", weight);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
