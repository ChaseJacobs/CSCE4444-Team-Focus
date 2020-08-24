package com.example.bhastings.workoutwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CaloriesBurnedRequest2;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.UpdateBestRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSSubmitConfirmationActivity extends AppCompatActivity {

    String username, distanceS ,bestdistanceS, weightS, timeS, besttimeS, totalBurnedS;
    TextView tvCalories, tvTime, tvDistance, tvUsername, tvBestTime, tvBestDistance;
    float caloriesF, weightF, timeF, timeBestF, distanceF ,bestdistanceF, totalBurnedF;
    Date besttime = null, time = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpssubmit_confirmation);

        Bundle bundle = this.getIntent().getExtras();

        username = bundle.getString("username");
        //username = "cjacobs";
        totalBurnedS = bundle.getString("burnedcalories");
        distanceS = bundle.getString("distance");
        timeS = bundle.getString("time"); //in milliseconds

        tvCalories = (TextView) findViewById(R.id.tvCaloriesBurned);
        tvCalories.setText(totalBurnedS);

        String originalString = timeS;
        //Date date = null;
        try {
            time = new SimpleDateFormat("HH:mm:ss:SSS").parse(originalString);
            timeS = new SimpleDateFormat("HH:mm:ss").format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //String newString = new SimpleDateFormat("H:mm:ss").format(date); // 9:00


        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText("Time: "+timeS);
        //timeF = Float.valueOf(timeS);

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDistance.setText("Distance: " + distanceS + "m");
        distanceF = Float.parseFloat(distanceS);

        //tvUsername = (TextView) findViewById(R.id.tvUserName);
        //tvUsername.setText(username);

        caloriesF = (float) 9.6;

        Button bHome = (Button) findViewById(R.id.home);
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GPSSubmitConfirmationActivity.this, UserAreaActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

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
                    //    Toast.makeText(GPSSubmitConfirmationActivity.this, "Value Bestdistance" + bestdistanceS, Toast.LENGTH_LONG).show();
                        besttimeS = jsonResponse.getString("besttime");

                        String originalString = besttimeS;
                        //Date date = null;
                        try {
                            besttime = new SimpleDateFormat("HH:mm:ss").parse(originalString);
                            //besttimeS = new SimpleDateFormat("HH:mm:ss").format(besttime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //convert to float values that round to two decimal places

                        //timeBestF = Float.valueOf(besttimeS);
                        bestdistanceF = Float.parseFloat(bestdistanceS);
                        weightF = Float.parseFloat(weightS);

                        //totalBurnedF = (caloriesF * weightF * timeF);
                        //totalBurnedS = (String.valueOf(totalBurnedF));

                        //set text view text of calories burned
                        tvCalories.setText(totalBurnedS);

                        //tvBestTime = (TextView) findViewById(R.id.tvBestTime);
                        //tvBestTime.setText(besttimeS);
                        //timeF = Float.valueOf(timeS);

                        //tvBestDistance = (TextView) findViewById(R.id.tvBestDistance);
                       // tvBestDistance.setText(bestdistanceS);
                        //distanceF = Float.valueOf(distanceS);

                        //multiply user's weight, calories burned, and time to get calories burned.
                        if(distanceF > bestdistanceF)
                        {
                            //Toast.makeText(GPSSubmitConfirmationActivity.this, "Value Bestdistance" + bestdistanceS, Toast.LENGTH_LONG).show();
                            bestdistanceF = distanceF;
                            bestdistanceS = String.valueOf(bestdistanceF);
                            besttimeS = timeS;

                            Toast.makeText(GPSSubmitConfirmationActivity.this, "New Bestdistance" + bestdistanceS, Toast.LENGTH_LONG).show();

                            tvBestDistance = (TextView) findViewById(R.id.tvBestDistance);
                            tvBestDistance.setText("New Best Distance: " + bestdistanceS);
                            //distanceF = Float.valueOf(distanceS);

                            //request with frequencyS and customname
                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");

                                        if(success){
                                      //      Toast.makeText(GPSSubmitConfirmationActivity.this, "Sent Bestdistance" + bestdistanceS, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(GPSSubmitConfirmationActivity.this, "Fail" , Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }

                            };


                            bestdistanceS = (String.valueOf(bestdistanceF));
                            //besttimeS = (String.valueOf(timeBestF));

                            UpdateBestRequest updateBestRequest = new UpdateBestRequest(username, bestdistanceS, besttimeS, responseListener2);
                            RequestQueue queue = Volley.newRequestQueue(GPSSubmitConfirmationActivity.this);
                            queue.add(updateBestRequest);
                        }

                    }
                    else
                        Toast.makeText(GPSSubmitConfirmationActivity.this, "Error: unable to retrieve calories for exercises.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CaloriesBurnedRequest2 caloriesBurnedRequest = new CaloriesBurnedRequest2(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(GPSSubmitConfirmationActivity.this);
        queue.add(caloriesBurnedRequest);

    }

}



