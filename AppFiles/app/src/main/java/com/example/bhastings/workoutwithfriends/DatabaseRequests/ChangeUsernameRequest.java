package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/13/2016.
 */

public class ChangeUsernameRequest extends StringRequest {
    private static final String CHANGE_USERNAME_URL = ServerConstants.CLOUD + "ChangeUsername.php";
    private Map<String, String> params;

    public ChangeUsernameRequest(String username, String newusername, Response.Listener listener){
        super(Method.POST, CHANGE_USERNAME_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("newusername", newusername);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
