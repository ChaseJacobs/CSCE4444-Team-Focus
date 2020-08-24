package com.example.bhastings.workoutwithfriends;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.EditProfileRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    Bundle bundle = new Bundle();
    String username;
    String firstname, lastname, age, weight, height, bio;
    EditText etFirstName, etLastName, etAge, etWeight, etHeight, etBio;
    ImageView imageToUpload;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etAge = (EditText) view.findViewById(R.id.etAge);
        etWeight = (EditText) view.findViewById(R.id.etWeight);
        etHeight = (EditText) view.findViewById(R.id.etHeight);
        etBio = (EditText) view.findViewById(R.id.etBio);



        Button bCancel = (Button) view.findViewById(R.id.bCancelEditProfile);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vProfile = new ProfileFragment();
                vProfile.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vProfile);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        Button bSave = (Button) view.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstname = etFirstName.getText().toString();
                lastname = etLastName.getText().toString();
                age = etAge.getText().toString();
                weight = etWeight.getText().toString();
                height = etHeight.getText().toString();
                bio = etBio.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getContext(), "Profile has been updated.",  Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                            e.printStackTrace();
                        }

                    }
                };

                if(firstname.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your First Name blank.", Toast.LENGTH_LONG).show();
                }
                else if(lastname.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your Last Name blank.", Toast.LENGTH_LONG).show();
                }
                else if(age.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your Age blank.", Toast.LENGTH_LONG).show();
                }
                else if(weight.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your Weight blank.", Toast.LENGTH_LONG).show();
                }
                else if(height.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your Height blank.", Toast.LENGTH_LONG).show();
                }
                else if(bio.isEmpty()){
                    Toast.makeText(getContext(), "Do not leave your Bio blank.", Toast.LENGTH_LONG).show();
                }
                else{
                    EditProfileRequest editProfileRequest = new EditProfileRequest(username, firstname, lastname, age, weight, height, bio, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(editProfileRequest);

                    Fragment vProfile = new ProfileFragment();
                    vProfile.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user_area, vProfile);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

        return view;
    }


}
