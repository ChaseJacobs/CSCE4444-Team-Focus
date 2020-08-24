package com.example.bhastings.workoutwithfriends;


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
import com.example.bhastings.workoutwithfriends.DatabaseRequests.UpdateWeightRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateWeightFragment extends Fragment {

    String username, weight;
    Bundle bundle = new Bundle();
    EditText etWeightUpdate;

    public UpdateWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        View view = inflater.inflate(R.layout.fragment_update_weight, container, false);

        etWeightUpdate = (EditText) view.findViewById(R.id.etWeightUpdate);


        Button bUpdateWeight = (Button) view.findViewById(R.id.bSubmitWeight);
        bUpdateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weight = etWeightUpdate.getText().toString();

                //send data to database with username and weight
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getContext(), "Weight has been upadted", Toast.LENGTH_LONG).show();

                                //show next screen
                                Fragment vDietView = new DietViewFragment();
                                vDietView.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_user_area, vDietView);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                            else{
                                Toast.makeText(getContext(), "Error: Unable to update weight.", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                UpdateWeightRequest updateWeightRequest = new UpdateWeightRequest(username, weight, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(updateWeightRequest);
            }
        });

        return view;
    }

}
