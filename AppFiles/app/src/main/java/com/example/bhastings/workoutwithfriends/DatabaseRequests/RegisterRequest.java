package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/10/2016.
 */



public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = ServerConstants.CLOUD + "Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String firstname, String lastname, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
