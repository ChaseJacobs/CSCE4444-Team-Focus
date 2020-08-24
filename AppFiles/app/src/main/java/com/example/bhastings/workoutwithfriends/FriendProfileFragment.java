package com.example.bhastings.workoutwithfriends;


import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ProfileRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.RemoveFriendRequest;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ServerConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendProfileFragment  extends Fragment {

    String username, homeuser, JSON_STRING, user;
    String firstname, lastname, age, weight, height, bio;
    TextView tvName, tvAge, tvWeight, tvHeight, tvBio;
    Bundle bundle = new Bundle();
    JSONArray jsonArray;

    ImageView ivProfilePicture;

    List<String> list = new ArrayList<String>();

    ListView workoutList;

    public FriendProfileFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("friend");
        user = username;
        homeuser = this.getArguments().getString("homeuser");
        bundle.putString("friend", username);
        bundle.putString("homeuser", homeuser);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);

        ivProfilePicture = (ImageView) view.findViewById(R.id.ivProfilePic);

        if(username.equals("cjacobs")){
            ivProfilePicture.setImageResource(R.drawable.chase);
        }
        else if(username.equals("bhastings")){
            ivProfilePicture.setImageResource(R.drawable.brandon);
        }

        workoutList = (ListView) view.findViewById(R.id.lvProfileWorkouts);
        new BackgroundTasks().execute();

        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;

                String value = (String) workoutList.getItemAtPosition(itemPosition);
                String page = "ViewFriendProfile";
                bundle.putString("homeuser", homeuser);
                bundle.putString("friend", username);
                bundle.putString("name", value);
                bundle.putString("page", page);
                android.support.v4.app.Fragment vWorkout = new FriendWorkoutFragment();
                vWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vWorkout);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        tvName = (TextView) view.findViewById(R.id.tvNameFriend);
        tvAge = (TextView) view.findViewById(R.id.tvAgeFriend);
        tvWeight = (TextView) view.findViewById(R.id.tvWeightFriend);
        tvHeight = (TextView) view.findViewById(R.id.tvHeightFriend);
        tvBio = (TextView) view.findViewById(R.id.tvBioFriend);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        firstname = jsonResponse.getString("firstname");
                        lastname = jsonResponse.getString("lastname");
                        age = jsonResponse.getString("age");
                        weight = jsonResponse.getString("weight");
                        height = jsonResponse.getString("height");
                        bio = jsonResponse.getString("bio");

                        tvName.setText(firstname + " " + lastname);
                        tvAge.setText(age);
                        tvWeight.setText(weight);
                        tvHeight.setText(height);
                        tvBio.setText(bio);

                    }

                } catch (JSONException e) {
                    Log.e("log_tag", "Error converting result " + e.toString());
                    e.printStackTrace();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(profileRequest);

        //view followers button
        Button bViewFollowers = (Button) view.findViewById(R.id.bViewFollowers);
        bViewFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment vViewFollowers = new ViewFollowersFragment();
                vViewFollowers.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vViewFollowers);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        Button bFriendWorkoutStats = (Button) view.findViewById(R.id.bViewFriendWorkoutStats);
        bFriendWorkoutStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vWorkoutStats = new FriendWorkoutStatsFragment();
                vWorkoutStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vWorkoutStats);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button bFriendDietStats = (Button) view.findViewById(R.id.bFriendDietStats);
        bFriendDietStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vDietStats = new FriendDietStatsFragment();
                vDietStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vDietStats);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //remove friend button
        Button bRemoveFriend = (Button) view.findViewById(R.id.bRemoveFriend);
        bRemoveFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //make request to remove friend from friend relations table
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getContext(), "You have unfollowed: " + username, Toast.LENGTH_SHORT).show();
                                //show user profile in nonfriend mode
                                Fragment vNonFriendFragment = new NonFriendProfileFragment();
                                vNonFriendFragment.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_user_area, vNonFriendFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                        } catch (JSONException e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                            e.printStackTrace();
                        }
                    }
                };

                RemoveFriendRequest removeFriendRequest = new RemoveFriendRequest(username, homeuser, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(removeFriendRequest);

            }
        });

        return view;
    }

    public class BackgroundTasks extends AsyncTask<Void, Void, String> {

        String JSON_URL;

        @Override
        protected void onPreExecute() {
            JSON_URL = ServerConstants.CLOUD + "GetWorkoutList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(user, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            // WORKOUTS_STRING = result;


            try {
                JSONObject jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("workoutList");
                int count=0;
                String workoutname;

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject JO = jsonArray.getJSONObject(i);
                    workoutname = JO.getString("name");

                    HashMap<String, String> workouts = new HashMap<>();

                    list.add(workoutname);
                }

                /*   while(count<jsonArray.length()){
                        JSONObject JO = jsonArray.getJSONObject(count);
                        workoutname = JO.getString("name");

                        HashMap<String, String> workouts = new HashMap<>();

                        list.add(workoutname);

                    //    workouts.put("name", workoutname);
                     //   workoutArrayList.add(workouts);

                        count++;
                    }
*/
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                //ArrayAdapter<HashMap<String, String>> workoutAdapter = new ArrayAdapter<HashMap<String, String>>(getContext(), android.R.layout.simple_list_item_1, workoutArrayList);

                workoutList.setAdapter(adapter);
                //workoutList.setAdapter(workoutAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
