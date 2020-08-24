package com.example.bhastings.workoutwithfriends;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.CheckIfFriends;
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
public class FriendsListFragment extends Fragment {

    String username, followee, JSON_STRING;
    Bundle bundle = new Bundle();
    ListView lvUserList;
    SearchView svUsers;
    JSONArray jsonArray;

    List<String> list = new ArrayList<String>();

    ArrayAdapter<String> adapter1;

    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("homeuser", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        svUsers = (SearchView) view.findViewById(R.id.svUsers);
        lvUserList = (ListView) view.findViewById(R.id.lvUserList);

        //fill data to lists

        new BackgroundTasks().execute();

        lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;
                final String value = (String) lvUserList.getItemAtPosition(itemPosition);
                followee = value;

                //check if you follow this person
                 Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                bundle.putString("friend", value);
                                Fragment vFriend = new FriendProfileFragment();
                                vFriend.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_user_area, vFriend);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }
                            else {
                                //if not their friend
                                bundle.putString("friend", value);
                                Fragment vFriend = new NonFriendProfileFragment();
                                vFriend.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_user_area, vFriend);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }

                        } catch (JSONException e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                            e.printStackTrace();
                        }
                    }
                };

                CheckIfFriends check = new CheckIfFriends(value, username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(check);

            }
        });

        return view;
    }

    public class BackgroundTasks extends AsyncTask<Void, Void, String> {

        String JSON_URL;

        @Override
        protected void onPreExecute() {
            JSON_URL = ServerConstants.CLOUD + "GetUsersList.php";
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
                jsonArray = jsonObject.getJSONArray("usersList");
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                //ArrayAdapter<HashMap<String, String>> workoutAdapter = new ArrayAdapter<HashMap<String, String>>(getContext(), android.R.layout.simple_list_item_1, workoutArrayList);

                lvUserList.setAdapter(adapter);
                //workoutList.setAdapter(workoutAdapter);

                svUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
