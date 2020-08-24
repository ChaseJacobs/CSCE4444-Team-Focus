package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        Button workoutStats = (Button) view.findViewById(R.id.bWorkoutStats);
        workoutStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vWorkoutStats = new WorkoutStatsFragment();
                vWorkoutStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vWorkoutStats);
                fragmentTransaction.commit();
            }
        });

        Button dietStats = (Button) view.findViewById(R.id.bDietStats);
        dietStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vDietStats = new DietStatsFragment();
                vDietStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vDietStats);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
