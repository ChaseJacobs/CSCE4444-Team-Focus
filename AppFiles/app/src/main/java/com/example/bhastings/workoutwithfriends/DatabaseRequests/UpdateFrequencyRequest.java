package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/14/2016.
 */

public class UpdateFrequencyRequest extends StringRequest {
    private static final String RECORD_MEAL_REQUEST = ServerConstants.CLOUD + "UpdateFrequency.php";
    private Map<String, String> params;

    public UpdateFrequencyRequest(String username, String customname, String frequency, Response.Listener<String> listener){
        super(Method.POST, RECORD_MEAL_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("customname", customname);
        params.put("frequency", frequency);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
