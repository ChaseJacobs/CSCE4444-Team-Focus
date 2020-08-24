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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.DeleteWorkoutRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.NewWorkoutRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ViewWorkoutRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendWorkoutFragment extends Fragment {

    Bundle bundle = new Bundle();
    String username, homeuser, name, page;

    String exercise1, exercise2, exercise3, exercise4, exercise5, exercise6, time1, time2, time3, time4, time5, time6;
    TextView tvExercise1, tvExercise2, tvExercise3, tvExercise4, tvExercise5, tvExercise6, tvOwner, tvWorkoutName,
            tvTime1, tvTime2, tvTime3, tvTime4, tvTime5, tvTime6;


    public FriendWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("friend");
        homeuser = this.getArguments().getString("homeuser");
        name = this.getArguments().getString("name");
        page = this.getArguments().getString("page");
        bundle.putString("username", username);
        bundle.putString("friend", username);
        bundle.putString("homeuser", homeuser);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_workout, container, false);

        tvTime1 = (TextView) view.findViewById(R.id.tvViewTime1);
        tvTime2 = (TextView) view.findViewById(R.id.tvViewTime2);
        tvTime3 = (TextView) view.findViewById(R.id.tvViewTime3);
        tvTime4 = (TextView) view.findViewById(R.id.tvViewTime4);
        tvTime5 = (TextView) view.findViewById(R.id.tvViewTime5);
        tvTime6 = (TextView) view.findViewById(R.id.tvViewTime6);
        tvExercise1 = (TextView) view.findViewById(R.id.tvViewExercise1);
        tvExercise2 = (TextView) view.findViewById(R.id.tvViewExercise2);
        tvExercise3 = (TextView) view.findViewById(R.id.tvViewExercise3);
        tvExercise4 = (TextView) view.findViewById(R.id.tvViewExercise4);
        tvExercise5 = (TextView) view.findViewById(R.id.tvViewExercise5);
        tvExercise6 = (TextView) view.findViewById(R.id.tvViewExercise6);
        tvWorkoutName = (TextView) view.findViewById(R.id.tvWorkoutName);
        tvOwner = (TextView) view.findViewById(R.id.tvOwner);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        exercise1 = jsonResponse.getString("exercise1");
                        exercise2 = jsonResponse.getString("exercise2");
                        exercise3 = jsonResponse.getString("exercise3");
                        exercise4 = jsonResponse.getString("exercise4");
                        exercise5 = jsonResponse.getString("exercise5");
                        exercise6 = jsonResponse.getString("exercise6");

                        time1 = jsonResponse.getString("time1");
                        time2 = jsonResponse.getString("time2");
                        time3 = jsonResponse.getString("time3");
                        time4 = jsonResponse.getString("time4");
                        time5 = jsonResponse.getString("time5");
                        time6 = jsonResponse.getString("time6");

                        //set values
                        tvExercise1.setText(exercise1);
                        tvExercise2.setText(exercise2);
                        tvExercise3.setText(exercise3);
                        tvExercise4.setText(exercise4);
                        tvExercise5.setText(exercise5);
                        tvExercise6.setText(exercise6);

                        tvTime1.setText(time1);
                        tvTime2.setText(time2);
                        tvTime3.setText(time3);
                        tvTime4.setText(time4);
                        tvTime5.setText(time5);
                        tvTime6.setText(time6);
                        tvOwner.setText(username);
                        tvWorkoutName.setText(name);

                    }
                    else{
                        Toast.makeText(getContext(), "Error: could not find workout", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ViewWorkoutRequest workoutRequest = new ViewWorkoutRequest(username, name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(workoutRequest);

        final Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        Toast.makeText(getContext(), "Workout was successfully downloaded.", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getContext(), "Error: could not download workout", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Button backButton = (Button) view.findViewById(R.id.bWorkoutViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page.equals("ViewProfile")){
                    Fragment vProfile = new ProfileFragment();
                    bundle.putString("homeuser", homeuser);
                    bundle.putString("friend", username);
                    vProfile.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user_area, vProfile);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if(page.equals("Activities")){
                    Fragment vActivities = new ActivitiesFragment();
                    vActivities.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user_area, vActivities);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if(page.equals("ViewFriendProfile")){
                    Fragment vFriendProfile = new FriendProfileFragment();
                    vFriendProfile.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user_area, vFriendProfile);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });

        Button bDownload = (Button) view.findViewById(R.id.bDownloadWorkout);
        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Download workout request
                NewWorkoutRequest downloadWorkout = new NewWorkoutRequest(homeuser, name, exercise1, exercise2, exercise3, exercise4,
                        exercise5, exercise6, time1, time2, time3, time4, time5, time6, responseListener2);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(downloadWorkout);
            }
        });

        return view;
    }

}
