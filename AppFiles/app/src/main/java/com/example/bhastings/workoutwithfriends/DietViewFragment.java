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
public class DietViewFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();
    public DietViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_view, container, false);

        Button bTodaysCalories = (Button) view.findViewById(R.id.bTodayCalories);
        bTodaysCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vTodaysCalories = new DietFragment();
                vTodaysCalories.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vTodaysCalories);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button bRecordMeal = (Button) view.findViewById(R.id.bRecordMeal);
        bRecordMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vRecordMeal = new RecordMealFragment();
                vRecordMeal.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vRecordMeal);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button bUpdateWeight = (Button) view.findViewById(R.id.bUpdateWeight);
        bUpdateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vUpdateWeight = new UpdateWeightFragment();
                vUpdateWeight.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vUpdateWeight);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button bDietView = (Button) view.findViewById(R.id.bDietStats);
        bDietView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vDietStats = new DietStatsFragment();
                vDietStats.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vDietStats);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

}
