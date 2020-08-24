package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/27/2016.
 */

public class CheckIfFriends extends StringRequest {
    private static final String CHECK_IF_FRIENDS_URL = ServerConstants.CLOUD + "CheckIfFriends.php";
    private Map<String, String> params;

    public CheckIfFriends(String followee, String follower, Response.Listener<String> listener){
        super(Request.Method.POST, CHECK_IF_FRIENDS_URL, listener, null);
        params = new HashMap<>();
        params.put("followee", followee);
        params.put("follower", follower);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
