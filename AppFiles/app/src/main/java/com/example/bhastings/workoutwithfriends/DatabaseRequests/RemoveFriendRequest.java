package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/27/2016.
 */

public class RemoveFriendRequest extends StringRequest{
    private static final String REMOVE_FRIEND_URL = ServerConstants.CLOUD + "RemoveFriend.php";
    private Map<String, String> params;

    public RemoveFriendRequest(String followee, String follower, Response.Listener<String> listener){
        super(Method.POST, REMOVE_FRIEND_URL, listener, null);
        params = new HashMap<>();
        params.put("followee", followee);
        params.put("follower", follower);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
