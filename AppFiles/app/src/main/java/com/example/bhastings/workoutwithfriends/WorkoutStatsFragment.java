package com.example.bhastings.workoutwithfriends;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.WorkoutStatsRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.Series;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * A simple {@link Fragment} subclass.
 */



public class WorkoutStatsFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    boolean add1 = false, add2 = false, add3 = false, add4 = false, add5 = false;


    public WorkoutStatsFragment() {
        // Required empty public constructor
    }

    String default1 = new String();
    String default2 = new String();
    String default3 = new String();
    String custom1 = new String();
    String custom2 = new String();
    String custom3 = new String();
    String custom4 = new String();
    String custom5 = new String();
    String custom1frequencyS = new String();
    String custom2frequencyS = new String();
    String custom3frequencyS = new String();
    String custom4frequencyS = new String();
    String custom5frequencyS = new String();
    String run = new String();

    float custom1frequencyF, custom2frequencyF, custom3frequencyF, custom4frequencyF, custom5frequencyF;



    public void fillPercentages(float[] percentages){
        //fill percentages
        for(int i=0; i<9; i++){
            percentages[i] = (float) 1/9;
        }
    }

    public void fillWorkouts(List<String> workouts, String[] Workouts, String custom1, String custom2, String custom3, String custom4, String custom5, int workoutCounter){
        //fill workouts

 //       default1 = "Default1";
//        default2 = "Default2";
 //       default3 = "Default3";
//        run = "Run";
//        workouts.add(default1);
 //       workouts.add(default2);
  //      workouts.add(default3);
 //       workouts.add(run);
        if(custom1.equals("NULL")){

        }
        else
            workouts.add(custom1);

            if(custom2.equals("NULL")) {

            }
            else{
                workouts.add(custom2);

                if(!(custom3.equals("NULL"))){

                }
                else {
                    workouts.add(custom3);

                    if(custom4.equals("NULL")){

                    }
                    else{
                        workouts.add(custom4);

                        if(!(custom5.equals("NULL"))){

                        }
                        else{
                            workouts.add(custom5);
                        }
                    }
                }
        }


        for (int i=0; i<workoutCounter; i++){
            Workouts[i] = workouts.get(i);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = this.getArguments().getString("username");
        bundle.putString("username", username);


        // Inflate the layout for this fragment

    final  View view = inflater.inflate(R.layout.fragment_workout_stats, container, false);

        //start of workout stats request
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    int workoutCounter = 0;
                    ArrayList<String> Workouts = new ArrayList<>();

                    if(success){
                        custom1 = jsonResponse.getString("custom1name");
                        String bestdistance = jsonResponse.getString("bestdistance");
                        String besttime = jsonResponse.getString("besttime");

                        TextView tvBestDistance = (TextView) view.findViewById(R.id.tvBestDistance);
                        tvBestDistance.setText(bestdistance);

                        TextView tvBestTime = (TextView) view.findViewById(R.id.tvBestTime);
                        tvBestTime.setText(besttime);

                        if(custom1.equals("NULL")){

                        }
                        else {
                            add1 = true;
                            workoutCounter = workoutCounter + 1;
                            custom1frequencyS = jsonResponse.getString("custom1frequency");
                            custom1frequencyF = Float.valueOf(custom1frequencyS);
                            custom2 = jsonResponse.getString("custom2name");
                            if(custom2.equals("NULL")){

                            }
                            else{
                                add2 = true;
                                workoutCounter = workoutCounter + 1;
                                custom2frequencyS = jsonResponse.getString("custom2frequency");
                                custom2frequencyF = Float.valueOf(custom2frequencyS);
                                custom3 = jsonResponse.getString("custom3name");
                                if(custom3.equals("NULL")){

                                }
                                else{
                                    add3 = true;
                                    workoutCounter = workoutCounter + 1;
                                    custom3frequencyS = jsonResponse.getString("custom3frequency");
                                    custom3frequencyF = Float.valueOf(custom3frequencyS);
                                    custom4 = jsonResponse.getString("custom4name");
                                    if(custom4.equals("NULL")){

                                    }
                                    else{
                                        add4 = true;
                                        workoutCounter = workoutCounter + 1;
                                        custom4frequencyS = jsonResponse.getString("custom4frequency");
                                        custom4frequencyF = Float.valueOf(custom4frequencyS);
                                        custom5 = jsonResponse.getString("custom5name");
                                        if(custom5.equals("NULL")){

                                        }
                                        else{
                                            add5 = true;
                                            workoutCounter = workoutCounter + 1;
                                            custom5frequencyS = jsonResponse.getString("custom5frequency");
                                            custom5frequencyF = Float.valueOf(custom5frequencyS);
                                        }//custom5
                                    }//custom4
                                }//custom3
                            }//custom2
                        } //custom1

                        float[] percentages = new float[workoutCounter];
                        String [] workouts = new String[workoutCounter];

                        //calculate percentages
                        float total = custom1frequencyF + custom2frequencyF + custom3frequencyF + custom4frequencyF + custom5frequencyF;

                        //fill float array
                        if(add1){
                           // Workouts.add(custom2);
                            percentages[0] = (float) custom1frequencyF/total;
                            if(add2){
                                percentages[1] = (float) custom2frequencyF/total;
                                if(add3){
                                    percentages[2] = (float) custom3frequencyF/total;
                                    if(add4){
                                        percentages[3] = (float) custom4frequencyF/total;
                                        if(add5){
                                            percentages[4] = (float) custom5frequencyF/total;
                                        }
                                    }
                                }
                            }
                        }

                        //fill workouts
                        if(custom1.equals("NULL")){

                        }
                        else {
                            Workouts.add(custom1);

                            if (custom2.equals("NULL")) {

                            } else {
                                Workouts.add(custom2);

                                if (custom3.equals("NULL")) {

                                } else {
                                    Workouts.add(custom3);

                                    if (custom4.equals("NULL")) {

                                    } else {
                                        Workouts.add(custom4);


                                        if (custom5.equals("NULL")) {

                                        } else {
                                            Workouts.add(custom5);

                                        }
                                    }
                                }
                            }
                        }
                        int[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.rgb(255, 160, 0), Color.GREEN, Color.GRAY, Color.MAGENTA, Color.rgb(255, 160, 0),Color.rgb(255,192,203), Color.rgb(222,184,135) };

                        CategorySeries distributionSeries = new CategorySeries("Workouts");

                        for(int i = 0; i < workoutCounter; i++){
                            distributionSeries.add(Workouts.get(i), percentages[i]);
                        }

                        DefaultRenderer defaultRenderer = new DefaultRenderer();
                        for(int i=0; i < percentages.length; i++) {
                            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                            seriesRenderer.setColor(colors[i]);
                            //   seriesRenderer.setDisplayChartValues(true);

                           // defaultRenderer.setBackgroundColor(Color.WHITE);
                            defaultRenderer.setLabelsTextSize(30f);
                            defaultRenderer.setLabelsColor(Color.BLACK);
                            defaultRenderer.setLegendTextSize(25f);
                            defaultRenderer.setApplyBackgroundColor(true);

                            defaultRenderer.addSeriesRenderer(seriesRenderer);
                        }
                        defaultRenderer.setChartTitle("Workout Report");
                        defaultRenderer.setChartTitleTextSize(60);
                        defaultRenderer.setZoomButtonsVisible(false);
                        defaultRenderer.setPanEnabled(false);

                        LinearLayout chartContainer = (LinearLayout) view.findViewById(R.id.graphWorkout);
                        View workoutChart = ChartFactory.getPieChartView(getContext(), distributionSeries, defaultRenderer);

                        chartContainer.addView(workoutChart);


                        //set text for best distance

                        //best time

                        //highest frequency
                        TextView mostCommonWorkout = (TextView) view.findViewById(R.id.tvMostCommonWorkout);

                        if(custom1frequencyF > custom2frequencyF && custom1frequencyF > custom3frequencyF && custom1frequencyF > custom4frequencyF && custom1frequencyF > custom5frequencyF){
                            mostCommonWorkout.setText(custom1);
                        }
                        else if(custom2frequencyF > custom1frequencyF && custom2frequencyF > custom3frequencyF && custom2frequencyF > custom4frequencyF && custom2frequencyF > custom5frequencyF){
                            mostCommonWorkout.setText(custom2);
                        }
                        else if(custom3frequencyF > custom1frequencyF && custom3frequencyF > custom2frequencyF && custom3frequencyF > custom4frequencyF && custom3frequencyF > custom5frequencyF){
                            mostCommonWorkout.setText(custom3);
                        }
                        else if(custom4frequencyF > custom1frequencyF && custom4frequencyF > custom2frequencyF && custom4frequencyF > custom3frequencyF && custom4frequencyF > custom5frequencyF){
                            mostCommonWorkout.setText(custom4);
                        }
                        else if(custom5frequencyF > custom1frequencyF && custom5frequencyF > custom2frequencyF && custom5frequencyF > custom3frequencyF && custom5frequencyF > custom4frequencyF){
                            mostCommonWorkout.setText(custom5);
                        }
                        else
                            mostCommonWorkout.setText("No workout is the highest. Most likely a tie has occurred between workouts.");


                    } //success

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };


        WorkoutStatsRequest workoutStatsRequest = new WorkoutStatsRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(workoutStatsRequest);



        Button bBackToStatsMenu = (Button) view.findViewById(R.id.bBackToStatsMenu);
        bBackToStatsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vStats = new StatisticsFragment();
                vStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vStats);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

            return view;
    }

}
