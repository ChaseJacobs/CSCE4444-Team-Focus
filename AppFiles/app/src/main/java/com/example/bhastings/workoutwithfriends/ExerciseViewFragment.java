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


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseViewFragment extends Fragment {

    Bundle bundle = new Bundle();
    String username;


    public ExerciseViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);

                // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_view, container, false);

        Button bBackToActivities = (Button) view.findViewById(R.id.bBackToActivities);

        bBackToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vActivities = new ActivitiesFragment();
                vActivities.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vActivities);
                fragmentTransaction.commit();
            }
        });

        return view;

        
    }

}
