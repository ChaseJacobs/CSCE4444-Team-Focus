package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.RecordMealRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordMealFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    String meal, caloriesEntered;


    public RecordMealFragment() {
        // Required empty public constructor
    }

    //check to see if fields are empty
    public boolean isEmpty(EditText etText){
        return etText.getText().toString().length() == 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_meal, container, false);

        //XML declarations
       final Spinner mealSpinner = (Spinner) view.findViewById(R.id.mealSpinner);
       final EditText calories = (EditText) view.findViewById(R.id.etCaloriesEntered);

        //Save data from user after Submit button is presssed
        Button bSubmit = (Button) view.findViewById(R.id.bSubmitMeal);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check to make sure values are not null
                if(isEmpty(calories)){
                    Toast.makeText(getActivity(), "You did not enter any calories", Toast.LENGTH_SHORT).show();
                }
                else {
                    //store values from user
                    meal = (String) mealSpinner.getSelectedItem().toString();
                    caloriesEntered = (String) calories.getText().toString();

                    //send data to database
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Toast.makeText(getActivity(), "Meal has been confirmed.", Toast.LENGTH_LONG).show();

                                    //display confirmation screen
                                    Fragment vDiet = new DietFragment();
                                    vDiet.setArguments(bundle);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content_user_area, vDiet);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Error, meal was unable to be confirmed.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RecordMealRequest mealRequest = new RecordMealRequest(username, meal, caloriesEntered, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(mealRequest);

                }
            }
        });

        return view;
    }

}
