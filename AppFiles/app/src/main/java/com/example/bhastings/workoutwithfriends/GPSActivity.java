package com.example.bhastings.workoutwithfriends;

/**
 * Created by cmj_7 on 11/21/2016.
 */

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class GPSActivity extends AppCompatActivity {

    String username, JSON_STRING;
    Bundle bundle;
    Bundle bundle2 = new Bundle();
    JSONArray jsonArray;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps; // = new GPSTracker(GPSActivity.this);

    String burnedcaloriesS, totalDistanceS, timeS;

    private double startLat;
    private double startLon;

    private double finalLat;
    private double finalLon;

    double CalPerMin = 9.6;
    int mins;

    /*timer
    TextView timer;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };
    */
    private TextView timer;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    float burnedcalories = 0;
    float totalDistance = 0;
    float time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gps = new GPSTracker(GPSActivity.this);

        Bundle bundle = this.getIntent().getExtras();

        username = bundle.getString("username");
        bundle.putString("username", username);
        bundle2.putString("username", username);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_gps);

        try {
            //noinspection deprecation
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       // timer = (TextView) findViewById(R.id.timer);

        //gps = new GPSTracker(GPSActivity.this);

        Button button5 = (Button) findViewById(R.id.b5);

        //button5.setText("Start Tracking");
        /*
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location loc1 = new Location("");
                loc1.setLatitude(startLat);
                loc1.setLongitude(startLon);

                Location loc2 = new Location("");
                loc2.setLatitude(finalLat);
                loc2.setLongitude(finalLon);

                float distanceInMeters = loc1.distanceTo(loc2);
                totalDistance += distanceInMeters;

                final TextView textViewToChange = (TextView) findViewById(R.id.d1);
                //textViewToChange.setTextSize(40);
                textViewToChange.setText("Distance " + totalDistance + "m");

                burnedcalories = (float) ((( (float)(updatedTime / 1000) ) /60) * CalPerMin);
                //time = updatedTime;

                //timeS = (String.valueOf(updatedTime));

                final TextView textViewToChange3= (TextView) findViewById(R.id.c1);
                //textViewToChange.setTextSize(40);
                textViewToChange3.setText("Calories Burned " + burnedcalories);
            }
        });
        */
        /*
        Button button1 = (Button) findViewById(R.id.b1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                gps = new GPSTracker(GPSActivity.this);

                double latitude;
                double longitude;
                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    final TextView textViewToChange = (TextView) findViewById(R.id.e1);
                    //textViewToChange.setTextSize(40);
                    textViewToChange.setText(
                            "Your Location is - \nLat: "
                                    + latitude + "\nLong: " + longitude);



                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }



                ///
                //textView.setText(message);

            }
        });
        */

        final Button button2 = (Button) findViewById(R.id.b2);
        timer = (TextView) findViewById(R.id.timer);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //startTime = 0L;
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);


                gps = new GPSTracker(GPSActivity.this);

                double latitude;
                double longitude;
                // check if GPS enabled
                if(gps.canGetLocation()){



                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    startLat = latitude;
                    startLon = longitude;

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    final TextView textViewToChange = (TextView) findViewById(R.id.e1);
                    //textViewToChange.setTextSize(40);
                    textViewToChange.setText(
                            "Your Location is - \nLat: "
                                    + latitude + "\nLong: " + longitude);

                    if (button2.getText().equals("Start Tracking")) {
                        button2.setText("Resume Tracking");
                    }
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                /*
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                startTime = SystemClock.uptimeMillis();

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    finalLat = latitude;
                    finalLon = longitude;

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    final TextView textViewToChange = (TextView) findViewById(R.id.e1);
                    //textViewToChange.setTextSize(40);
                    textViewToChange.setText(
                            "Your Location is - \nLat: "
                                    + latitude + "\nLong: " + longitude);
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
*/


                ///
                //textView.setText(message);





                ///
                //textView.setText(message);


            }
        });

        Button button4 = (Button) findViewById(R.id.b4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                startTime = SystemClock.uptimeMillis();



                gps = new GPSTracker(GPSActivity.this);

                double latitude;
                double longitude;
                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    finalLat = latitude;
                    finalLon = longitude;

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    final TextView textViewToChange = (TextView) findViewById(R.id.e1);
                    //textViewToChange.setTextSize(40);
                    textViewToChange.setText(
                            "Your Location is - \nLat: "
                                    + latitude + "\nLong: " + longitude);
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }



                ///
                //textView.setText(message);
                Location loc1 = new Location("");
                loc1.setLatitude(startLat);
                loc1.setLongitude(startLon);

                Location loc2 = new Location("");
                loc2.setLatitude(finalLat);
                loc2.setLongitude(finalLon);

                float distanceInMeters = loc1.distanceTo(loc2);
                totalDistance += distanceInMeters;

                final TextView textViewToChange = (TextView) findViewById(R.id.d1);
                //textViewToChange.setTextSize(40);
                textViewToChange.setText("Distance " + totalDistance + "m");

                burnedcalories = (float) ((( (float)(updatedTime / 1000) ) /60) * CalPerMin);
                //time = updatedTime;

                //timeS = (String.valueOf(updatedTime));

                final TextView textViewToChange3= (TextView) findViewById(R.id.c1);
                //textViewToChange.setTextSize(40);
                textViewToChange3.setText("Calories Burned " + burnedcalories);

            }
        });


        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                burnedcaloriesS = (String.valueOf(burnedcalories));
                bundle2.putString("burnedcalories", burnedcaloriesS);
                totalDistanceS = (String.valueOf(totalDistance));
                bundle2.putString("distance", totalDistanceS);
                //timeS = (String.valueOf(time));
                bundle2.putString("time", timeS);

                Intent intent = new Intent(GPSActivity.this, GPSSubmitConfirmationActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);


                /*
                Fragment vConfirmWorkout = new GPSSubmitConfirmationFragment();
                vConfirmWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vConfirmWorkout);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */
            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("Timer " + mins + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));

            timeS = ("00:" + String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    /*
    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button button5 = (Button)findViewById(R.id.b5);
        button5.setText("Start Tracking");
    }
*/
}