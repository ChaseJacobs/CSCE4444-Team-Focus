package com.example.bhastings.workoutwithfriends;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ChangePasswordRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ChangeUsernameRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    String password1, password2, currentpassword, username, newusername;

    Bundle bundle = new Bundle();

    EditText etCurrentPassword, etPassword1, etPassword2, etNewUsername;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        username = getArguments().getString("username");


        etNewUsername = (EditText) view.findViewById(R.id.etNewUsername);
        etCurrentPassword = (EditText) view.findViewById(R.id.etCurrentPassword);
        etPassword1 = (EditText) view.findViewById(R.id.etChangePassword1);
        etPassword2 = (EditText) view.findViewById(R.id.etChangePassword2);

        Button bChangeUsername = (Button) view.findViewById(R.id.bChangeUsername);
        bChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newusername = etNewUsername.getText().toString();

                if(newusername.equals(username)){
                    Toast.makeText(getContext(), "Error: This is your current username", Toast.LENGTH_SHORT).show();
                }
                else if(newusername.isEmpty()){
                    Toast.makeText(getContext(), "Error: Field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResposne = new JSONObject(response);
                                boolean success = jsonResposne.getBoolean("success");
                                String reply;

                                if (success) {
                                    Toast.makeText(getContext(), "Username has been changed", Toast.LENGTH_SHORT).show();

                                    Fragment Home = new HomeFragment();
                                    bundle.putString("username", newusername);
                                    Intent userAreaIntent = new Intent(getActivity(), UserAreaActivity.class);
                                    userAreaIntent.putExtras(bundle);
                                    startActivity(userAreaIntent);
                                } else{
                                    reply = jsonResposne.getString("reason");
                                    if(reply.equals("exists")){
                                        Toast.makeText(getContext(), "Unable to change username. Username already exists", Toast.LENGTH_LONG).show();
                                    }
                                    else if(reply.equals("username not found")){
                                        Toast.makeText(getContext(), "Unable to change username. Current username was not found.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ChangeUsernameRequest changeUsername = new ChangeUsernameRequest(username, newusername, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(changeUsername);
                }
            }
        });

        Button bChangePassword = (Button) view.findViewById(R.id.bChangePassword);
        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentpassword = etCurrentPassword.getText().toString();
                password1 = etPassword1.getText().toString();
                password2 = etPassword2.getText().toString();

                if (password1.isEmpty() || password2.isEmpty()) { //fields are empty
                    Toast.makeText(getContext(), "Error: Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password1.equals(password2)) {
                        if (password1.equals(currentpassword) && password2.equals(currentpassword)) { //current matches new password
                            Toast.makeText(getContext(), "Error: Current Password cannot be the same as new password", Toast.LENGTH_SHORT).show();
                        } else {
                            //save password
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResposne = new JSONObject(response);
                                        boolean success = jsonResposne.getBoolean("success");

                                        if (success) {
                                            Toast.makeText(getContext(), "Password has been changed", Toast.LENGTH_SHORT).show();

                                            Fragment Home = new HomeFragment();
                                            bundle.putString("username", username);
                                            Home.setArguments(bundle);
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.content_user_area, Home);
                                            fragmentTransaction.commit();
                                        } else
                                            Toast.makeText(getContext(), "Unable to change password. Make sure you entered your current password correctly", Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            ChangePasswordRequest changePassword = new ChangePasswordRequest(username, currentpassword, password1, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(changePassword);
                        }
                    } else
                        Toast.makeText(getContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

}
