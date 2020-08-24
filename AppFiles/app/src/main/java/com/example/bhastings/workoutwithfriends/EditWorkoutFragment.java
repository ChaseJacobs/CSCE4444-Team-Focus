package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.EditWorkoutRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkoutFragment extends Fragment {

    String username, oldname, page;
    Bundle bundle = new Bundle();

    String name, exercise1, exercise2, exercise3, exercise4, exercise5, exercise6,
            time1, time2, time3, time4, time5, time6;


    public EditWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = this.getArguments().getString("username");
        oldname = this.getArguments().getString("name");
        page = this.getArguments().getString("page");
        bundle.putString("username", username);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        final Spinner sExercise1 = (Spinner) view.findViewById(R.id.sEditExercise1);
        final Spinner sExercise2 = (Spinner) view.findViewById(R.id.sEditExercise2);
        final Spinner sExercise3 = (Spinner) view.findViewById(R.id.sEditExercise3);
        final Spinner sExercise4 = (Spinner) view.findViewById(R.id.sEditExercise4);
        final Spinner sExercise5 = (Spinner) view.findViewById(R.id.sEditExercise5);
        final Spinner sExercise6 = (Spinner) view.findViewById(R.id.sEditExercise6);

        final EditText etWorkoutName = (EditText) view.findViewById(R.id.etEditWorkoutName);

        final EditText etTime1 = (EditText) view.findViewById(R.id.etEditTime1);
        final EditText etTime2 = (EditText) view.findViewById(R.id.etEditTime2);
        final EditText etTime3 = (EditText) view.findViewById(R.id.etEditTime3);
        final EditText etTime4 = (EditText) view.findViewById(R.id.etEditTime4);
        final EditText etTime5 = (EditText) view.findViewById(R.id.etEditTime5);
        final EditText etTime6 = (EditText) view.findViewById(R.id.etEditTime6);

        Button bCancel = (Button) view.findViewById(R.id.bCancelEditWorkout);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vWorkout = new WorkoutViewFragment();
                bundle.putString("page", page);
                bundle.putString("name", oldname);
                vWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vWorkout);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        Button bSave = (Button) view.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etWorkoutName.getText().toString();
                exercise1 = sExercise1.getSelectedItem().toString();
                exercise2 = sExercise2.getSelectedItem().toString();
                exercise3 = sExercise3.getSelectedItem().toString();
                exercise4 = sExercise4.getSelectedItem().toString();
                exercise5 = sExercise5.getSelectedItem().toString();
                exercise6 = sExercise6.getSelectedItem().toString();

                time1 = etTime1.getText().toString();
                time2 = etTime2.getText().toString();
                time3 = etTime3.getText().toString();
                time4 = etTime4.getText().toString();
                time5 = etTime5.getText().toString();
                time6 = etTime6.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                if (name.isEmpty()) {
                                    Toast.makeText(getActivity(), "Name can not be blank", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "Workout has been updated.", Toast.LENGTH_LONG).show();

                                    Fragment vWorkout = new WorkoutViewFragment();
                                    bundle.putString("name", name);
                                    vWorkout.setArguments(bundle);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content_user_area, vWorkout);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            }
                            else{
                                    Toast.makeText(getActivity(), "Error: Workout was not updated, please try again.", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                            e.printStackTrace();
                        }


                    }
                };

                EditWorkoutRequest editWorkoutRequest = new EditWorkoutRequest(username, oldname, name, exercise1, exercise2, exercise3, exercise4, exercise5, exercise6,
                        time1, time2, time3, time4, time5, time6, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(editWorkoutRequest);

            }
        });

        return view;
    }

}
