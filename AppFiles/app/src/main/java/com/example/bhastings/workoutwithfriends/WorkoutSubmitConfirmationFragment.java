package com.example.bhastings.workoutwithfriends;


import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CaloriesBurnedRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.UpdateFrequencyRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutSubmitConfirmationFragment extends Fragment {

    String username, name, customname, caloriesS1, caloriesS2, caloriesS3, caloriesS4, caloriesS5, caloriesS6, weightS,
            time1S, time2S, time3S, time4S, time5S, time6S, totalBurnedS, custom1name, custom1frequencyS,
            custom2name, custom2frequencyS, custom3name, custom3frequencyS, custom4name, custom4frequencyS, custom5name, custom5frequencyS, frequencyS;
    TextView tvCalories;
    float caloriesF1, caloriesF2, caloriesF3, caloriesF4, caloriesF5, caloriesF6, weightF, time1F, time2F,
            time3F, time4F, time5F, time6F, total1, total2, total3, total4, total5, total6, totalBurnedF;

    int frequency;

    public WorkoutSubmitConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
        name = getArguments().getString("name");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_submit_confirmation, container, false);

        tvCalories = (TextView) view.findViewById(R.id.tvCaloriesBurned);

        //request for frequency of chosen workout

        //update frequency of workout

        //request for calories by exercise name
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        //store values from exercises
                        caloriesS1 = jsonResponse.getString("calories1");
                        time1S = jsonResponse.getString("time1");
                        caloriesS2 = jsonResponse.getString("calories2");
                        time2S = jsonResponse.getString("time2");
                        caloriesS3 = jsonResponse.getString("calories3");
                        time3S = jsonResponse.getString("time3");
                        caloriesS4 = jsonResponse.getString("calories4");
                        time4S = jsonResponse.getString("time4");
                        caloriesS5 = jsonResponse.getString("calories5");
                        time5S = jsonResponse.getString("time5");
                        caloriesS6 = jsonResponse.getString("calories6");
                        time6S = jsonResponse.getString("time6");
                        weightS = jsonResponse.getString("weight");


                        //get name and frequency and increment the number before sending
                        custom1name = jsonResponse.getString("custom1name");
                        custom1frequencyS = jsonResponse.getString("custom1frequency");
                        custom2name = jsonResponse.getString("custom2name");
                        custom2frequencyS = jsonResponse.getString("custom2frequency");
                        custom3name = jsonResponse.getString("custom3name");
                        custom3frequencyS = jsonResponse.getString("custom3frequency");
                        custom4name = jsonResponse.getString("custom4name");
                        custom4frequencyS = jsonResponse.getString("custom4frequency");
                        custom5name = jsonResponse.getString("custom5name");
                        custom5frequencyS = jsonResponse.getString("custom5frequency");

                        //check for correct custom workout
                       if(custom1name.equals(name)){
                            frequency = Integer.valueOf(custom1frequencyS);
                            customname = custom1name;
                        }
                        else if(custom2name.equals(name)){
                            frequency = Integer.valueOf(custom2frequencyS);
                            customname = custom2name;
                        }
                        else if(custom3name.equals(name)){
                            frequency = Integer.valueOf(custom3frequencyS);
                            customname = custom3name;
                        }
                        else if (custom4name.equals(name)){
                            frequency = Integer.valueOf(custom4frequencyS);
                            customname = custom4name;
                        }
                        else if (custom5name.equals(name)){
                            frequency = Integer.valueOf(custom5frequencyS);
                            customname = custom5name;
                        }

                        //convert to float values that round to two decimal places
                        caloriesF1 = Float.valueOf(caloriesS1);
                        time1F = Float.valueOf(time1S);
                        caloriesF2 = Float.valueOf(caloriesS2);
                        time2F = Float.valueOf(time2S);
                        caloriesF3 = Float.valueOf(caloriesS3);
                        time3F = Float.valueOf(time3S);
                        caloriesF4 = Float.valueOf(caloriesS4);
                        time4F = Float.valueOf(time4S);
                        caloriesF5 = Float.valueOf(caloriesS5);
                        time5F = Float.valueOf(time5S);
                        caloriesF6 = Float.valueOf(caloriesS6);
                        time6F = Float.valueOf(time6S);
                        weightF = Float.valueOf(weightS);

                        //multiply user's weight, calories burned, and time to get calories burned.
                        total1 = (caloriesF1 * weightF * time1F);
                        total2 = (caloriesF2 * weightF * time2F);
                        total3 = (caloriesF3 * weightF * time3F);
                        total4 = (caloriesF4 * weightF * time4F);
                        total5 = (caloriesF5 * weightF * time5F);
                        total6 = (caloriesF6 * weightF * time6F);

                        //calculate total burned
                        totalBurnedF = total1 + total2 + total3 + total4 + total5 + total6;
                        totalBurnedS = (String.valueOf(totalBurnedF));

                        //set text view text of calories burned
                        tvCalories.setText(totalBurnedS);

                        //send request for new frequency addition
                        frequency = frequency + 1;
                        frequencyS = String.valueOf(frequency);

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

                        UpdateFrequencyRequest updateFrequencyRequest = new UpdateFrequencyRequest(username, customname, frequencyS, responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(updateFrequencyRequest);

                    }
                    else
                        Toast.makeText(getContext(), "Error: unable to retrieve calories for exercises.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CaloriesBurnedRequest caloriesBurnedRequest = new CaloriesBurnedRequest(username, name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(caloriesBurnedRequest);


        return view;
    }

}
