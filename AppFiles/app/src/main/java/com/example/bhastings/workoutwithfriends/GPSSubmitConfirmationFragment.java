package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CaloriesBurnedRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CaloriesBurnedRequest2;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.UpdateBestRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class GPSSubmitConfirmationFragment extends Fragment {

    String username, distanceS ,bestdistanceS, weightS, timeS, besttimeS, totalBurnedS;
    TextView tvCalories;
    float caloriesF, weightF, timeF, timeBestF, distanceF ,bestdistanceF, totalBurnedF;

    public GPSSubmitConfirmationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
        totalBurnedS = getArguments().getString("burnedcalories");
        distanceS = getArguments().getString("distance");
        timeS = getArguments().getString("time"); //in milliseconds

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_submit_confirmation, container, false);

        tvCalories = (TextView) view.findViewById(R.id.tvCaloriesBurned);

        caloriesF = (float) 9.6;
        //request for frequency of chosen workout

        //update frequency of workout

        //request for calories by exercise name
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        //store values from exercises
                        weightS = jsonResponse.getString("weight");
                        bestdistanceS = jsonResponse.getString("bestdistance");
                        besttimeS = jsonResponse.getString("besttime");

                        //convert to float values that round to two decimal places
                        timeBestF = Float.valueOf(besttimeS);
                        bestdistanceF = Float.valueOf(bestdistanceS);
                        weightF = Float.valueOf(weightS);

                        //multiply user's weight, calories burned, and time to get calories burned.
                        if(distanceF > bestdistanceF)
                        {
                            bestdistanceF = distanceF;
                            timeBestF = timeF;

                            //request with frequencyS and customname
                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");

                                        if(success){

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };


                            bestdistanceS = (String.valueOf(bestdistanceF));
                            besttimeS = (String.valueOf(timeBestF));

                            UpdateBestRequest updateBestRequest = new UpdateBestRequest(username, bestdistanceS, besttimeS, responseListener2);
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(updateBestRequest);
                        }

                        totalBurnedF = (caloriesF * weightF * timeF);
                        totalBurnedS = (String.valueOf(totalBurnedF));

                        //set text view text of calories burned
                        tvCalories.setText(totalBurnedS);



                    }
                    else
                        Toast.makeText(getContext(), "Error: unable to retrieve calories for exercises.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CaloriesBurnedRequest2 caloriesBurnedRequest = new CaloriesBurnedRequest2(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(caloriesBurnedRequest);


        return view;
    }

}
