package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/27/2016.
 */

public class AddFriendRequest extends StringRequest{
    private static final String ADD_FRIEND_URL = ServerConstants.CLOUD + "AddFriend.php";
    private Map<String, String> params;

    public AddFriendRequest(String followee, String follower, Response.Listener<String> listener){
        super(Request.Method.POST, ADD_FRIEND_URL, listener, null);
        params = new HashMap<>();
        params.put("followee", followee);
        params.put("follower", follower);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
