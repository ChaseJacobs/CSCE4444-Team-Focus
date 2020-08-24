package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/13/2016.
 */

public class ChangePasswordRequest extends StringRequest {
    private static final String CHANGE_PASSWORD_URL = ServerConstants.CLOUD + "ChangePassword.php";
    private Map<String, String> params;

    public ChangePasswordRequest(String username, String currentPassword, String password, Response.Listener listener){
        super(Method.POST, CHANGE_PASSWORD_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("currentpassword", currentPassword);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
