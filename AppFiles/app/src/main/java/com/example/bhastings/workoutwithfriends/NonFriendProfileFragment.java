package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.AddFriendRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CheckIfFriends;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ProfileRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class NonFriendProfileFragment extends Fragment {

    String username, homeuser;
    String firstname, lastname, age, weight, height, bio;
    TextView tvName, tvAge, tvWeight, tvHeight, tvBio;
    Bundle bundle = new Bundle();
    ImageView ivProfilePicture;


    public NonFriendProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = this.getArguments().getString("friend");
        homeuser = this.getArguments().getString("homeuser");
        bundle.putString("friend", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_friend_profile, container, false);

        ivProfilePicture = (ImageView) view.findViewById(R.id.ivProfilePic);

        if(username.equals("cjacobs")){
            ivProfilePicture.setImageResource(R.drawable.chase);
        }

        tvName = (TextView) view.findViewById(R.id.tvNameNonFriend);
        tvAge = (TextView) view.findViewById(R.id.tvAgeNonFriend);
        tvWeight = (TextView) view.findViewById(R.id.tvWeightNonFriend);
        tvHeight = (TextView) view.findViewById(R.id.tvHeightNonFriend);
        tvBio = (TextView) view.findViewById(R.id.tvBioNonFriend);

        //request user profile data
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        firstname = jsonResponse.getString("firstname");
                        lastname = jsonResponse.getString("lastname");
                        age = jsonResponse.getString("age");
                        weight = jsonResponse.getString("weight");
                        height = jsonResponse.getString("height");
                        bio = jsonResponse.getString("bio");

                        tvName.setText(firstname + " " + lastname);
                        tvAge.setText(age);
                        tvWeight.setText(weight);
                        tvHeight.setText(height);
                        tvBio.setText(bio);

                    }

                } catch (JSONException e) {
                    Log.e("log_tag", "Error converting result " + e.toString());
                    e.printStackTrace();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(profileRequest);


        //follow friend button pressed
        Button bAddFriend = (Button) view.findViewById(R.id.bAddFriend);
        bAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //make request to add friend to friend relations table
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getContext(), "You are now following: " + username, Toast.LENGTH_SHORT).show();

                                //show user profile in friend mode
                                bundle.putString("homeuser", homeuser);
                                Fragment vFriendFragment = new FriendProfileFragment();
                                vFriendFragment.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_user_area, vFriendFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                            else {
                                //if not able to follow
                                Toast.makeText(getContext(), "Error: Unable to follow this user.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                            e.printStackTrace();
                        }
                    }
                };

                AddFriendRequest addFriendRequest = new AddFriendRequest(username, homeuser, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(addFriendRequest);

            }
        });

        return view;
    }

}
