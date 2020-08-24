package com.example.bhastings.workoutwithfriends;


import android.os.AsyncTask;
import android.os.Bundle;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.ProfileRequest;
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
public class ProfileFragment extends Fragment {

    String username, JSON_STRING;
    String editUsername, firstname, lastname, age, weight, height, bio;
    TextView tvName, tvAge, tvWeight, tvHeight, tvBio;
    Bundle bundle = new Bundle();
    JSONArray jsonArray;

    List<String> list = new ArrayList<String>();

    ListView workoutList;

    ImageView profilePic;

    public ProfileFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic = (ImageView) view.findViewById(R.id.ivProfilePic);

        profilePic.setImageResource(R.drawable.no_pic);

        if(username.equals("cjacobs") || username.equals("cjacobs1")){
            profilePic.setImageResource(R.drawable.chase);
        }
        else if (username.equals("bhastings")){
            profilePic.setImageResource(R.drawable.brandon);
        }

        workoutList = (ListView) view.findViewById(R.id.lvProfileWorkouts);
        new BackgroundTasks().execute();

        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;

                String value = (String) workoutList.getItemAtPosition(itemPosition);
                String page = "ViewProfile";
                bundle.putString("homeuser", username);
                bundle.putString("name", value);
                bundle.putString("page", page);
                Fragment vWorkout = new WorkoutViewFragment();
                vWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vWorkout);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAge = (TextView) view.findViewById(R.id.tvAge);
        tvWeight = (TextView) view.findViewById(R.id.tvWeight);
        tvHeight = (TextView) view.findViewById(R.id.tvHeight);
        tvBio = (TextView) view.findViewById(R.id.tvBio);

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
        Button bViewFollowers = (Button) view.findViewById(R.id.bYourFollowers);
        bViewFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment vViewFollowers = new ViewFollowersFragment();
                bundle.putString("friend", username);
                vViewFollowers.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vViewFollowers);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        TextView tvEditProfile = (TextView) view.findViewById(R.id.tvEditProfile);
        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment editProfile = new EditProfileFragment();

                editProfile.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, editProfile);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
                String data = URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(username, "UTF-8");
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
