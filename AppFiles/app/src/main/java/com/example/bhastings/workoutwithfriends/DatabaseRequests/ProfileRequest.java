package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/13/2016.
 */

public class ProfileRequest extends StringRequest {
    private static final String PROFILE_REQUEST_URL = ServerConstants.CLOUD + "ViewProfile.php";
    private Map<String, String> params;

    public ProfileRequest(String username, Response.Listener listener){
        super(Method.POST, PROFILE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
