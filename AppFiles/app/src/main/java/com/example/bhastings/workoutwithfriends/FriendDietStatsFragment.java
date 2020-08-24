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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendDietStatsFragment extends Fragment {

    String username, homeuser;
    Bundle bundle = new Bundle();

    public FriendDietStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("friend");
        homeuser = this.getArguments().getString("homeuser");
        bundle.putString("friend", username);
        bundle.putString("homeuser", homeuser);


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_friend_diet_stats, container, false);

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            String breakfastTotal, lunchTotal, dinnerTotal, snackTotal, averageCalories;

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        breakfastTotal = jsonResponse.getString("Breakfast");
                        lunchTotal = jsonResponse.getString("Lunch");
                        dinnerTotal = jsonResponse.getString("Dinner");
                        snackTotal = jsonResponse.getString("Snack");

                        GraphView dietGraph = (GraphView) view.findViewById(R.id.graphWorkout); //declare graph

                        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(dietGraph); //declare label formatting variable
                        staticLabelsFormatter.setHorizontalLabels(new String[] {"Snack", "Breakfast", "Lunch", "Dinner"}); //initialize x axis labels

                        //declare meal variables
                        int snack, breakfast, lunch, dinner, totalCalories;
                        float average;

                        //assign caloric values
                        snack = Integer.parseInt(snackTotal);
                        breakfast = Integer.parseInt(breakfastTotal);
                        lunch = Integer.parseInt(lunchTotal);
                        dinner = Integer.parseInt(dinnerTotal);
                        int totalMeals = 4;
                        totalCalories = snack + breakfast + lunch + dinner;
                        if(snack == 0){
                            totalMeals = totalMeals - 1;
                        }
                        if(breakfast == 0){
                            totalMeals = totalMeals - 1;
                        }
                        if(lunch == 0){
                            totalMeals = totalMeals - 1;
                        }
                        if(dinner == 0){
                            totalMeals = totalMeals - 1;
                        }

                        if(totalMeals == 0){
                            totalMeals = 1;
                        }

                        average = (float) totalCalories / totalMeals;

                        averageCalories = String.valueOf(average);

                        TextView averageCaloricIntake = (TextView) view.findViewById(R.id.tvFriendAverageIntakeValue);
                        averageCaloricIntake.setText(averageCalories);

                        //fill graph data
                        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                                new DataPoint(0, snack),
                                new DataPoint(1, breakfast),
                                new DataPoint(2, lunch),
                                new DataPoint(3, dinner),

                        });

                        dietGraph.addSeries(series); //pass data to the graph
                        series.setSpacing(20); //set spacing for values
                        dietGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter); //render graph to screen
                        dietGraph.setTitle("Caloric Intake Report (Today)"); //assign graph title
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        DietStatsRequest dietStatsRequest = new DietStatsRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(dietStatsRequest);

        final Button backToProfile = (Button) view.findViewById(R.id.bBackToFriendProfile);
        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment FriendProfile = new FriendProfileFragment();
                FriendProfile.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, FriendProfile);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
