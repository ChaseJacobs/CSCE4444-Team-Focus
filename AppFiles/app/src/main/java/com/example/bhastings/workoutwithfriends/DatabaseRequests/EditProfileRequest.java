package com.example.bhastings.workoutwithfriends.DatabaseRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhastings on 11/14/2016.
 */

public class EditProfileRequest extends StringRequest {

    private static final String EDIT_PROFILE_REQUEST_URL = ServerConstants.CLOUD + "EditProfile.php";
    private Map<String, String> params;

    public EditProfileRequest(String username, String firstname, String lastname, String age, String weight, String height, String bio,  Response.Listener listener){
        super(Method.POST, EDIT_PROFILE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("age", age);
        params.put("weight", weight);
        params.put("height", height);
        params.put("bio", bio);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
