package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.DietStatsRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    TextView tvBreakfastCalories, tvLunchCalories, tvDinnerCalories, tvSnackCalories;
    String breakfastCalories, lunchCalories, dinnerCalories, snackCalories;

    public DietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        View view = inflater.inflate(R.layout.fragment_diet, container, false);
        final TextView tvBreakfastCalories, tvLunchCalories, tvDinnerCalories, tvSnackCalories;

        tvBreakfastCalories = (TextView) view.findViewById(R.id.tvBreakfastCalories);
        tvLunchCalories = (TextView) view.findViewById(R.id.tvLunchCalories);
        tvDinnerCalories = (TextView) view.findViewById(R.id.tvDinnerCalories);
        tvSnackCalories = (TextView) view.findViewById(R.id.tvSnackCalories);

           Response.Listener<String> responseListener = new Response.Listener<String>() {

            String breakfastCalories, lunchCalories, dinnerCalories, snackCalories;

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        breakfastCalories = jsonResponse.getString("Breakfast");
                        lunchCalories = jsonResponse.getString("Lunch");
                        dinnerCalories = jsonResponse.getString("Dinner");
                        snackCalories = jsonResponse.getString("Snack");

                        tvBreakfastCalories.setText(breakfastCalories);
                        tvLunchCalories.setText(lunchCalories);
                        tvDinnerCalories.setText(dinnerCalories);
                        tvSnackCalories.setText(snackCalories);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //rectrieve data from database
        DietStatsRequest dietStatsRequest = new DietStatsRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(dietStatsRequest);

        Button bBackToDietMenu = (Button) view.findViewById(R.id.bBackToDiet);
        bBackToDietMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vDietMenu = new DietViewFragment();
                vDietMenu.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vDietMenu);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }




}
