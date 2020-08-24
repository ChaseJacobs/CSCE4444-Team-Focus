package com.example.bhastings.workoutwithfriends;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
public class AddWorkoutFragment extends Fragment {

    String username, JSON_STRING;
    Bundle bundle = new Bundle();
    JSONArray jsonArray;


    List<String> list = new ArrayList<String>();

    ListView workoutList;
    Spinner sAddWorkoutList;
    String workoutname;

    public AddWorkoutFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_workout, container, false);

        workoutList = (ListView) view.findViewById(R.id.lvProfileWorkouts);
        new BackgroundTasks().execute();

        sAddWorkoutList = (Spinner) view.findViewById(R.id.sAddWorkoutList);



        Button bSubmit = (Button) view.findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutname = sAddWorkoutList.getSelectedItem().toString();
                bundle.putString("name", workoutname);
                Fragment vConfirmWorkout = new WorkoutSubmitConfirmationFragment();
                vConfirmWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vConfirmWorkout);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        Button bGo = (Button) view.findViewById(R.id.button2);
        bGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), GPSActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sAddWorkoutList.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                //ArrayAdapter<HashMap<String, String>> workoutAdapter = new ArrayAdapter<HashMap<String, String>>(getContext(), android.R.layout.simple_list_item_1, workoutArrayList);

//                workoutList.setAdapter(adapter);
                //workoutList.setAdapter(workoutAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
