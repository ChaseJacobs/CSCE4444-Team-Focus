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
public class HomeFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       username = this.getArguments().getString("username");
       bundle.putString("username", username);



        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button bHomeAddWorkout = (Button) view.findViewById(R.id.bHomeStartWorkout);
        bHomeAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vAddWorkout = new AddWorkoutFragment();
                vAddWorkout.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vAddWorkout);
                fragmentTransaction.commit();
            }
        });

        Button bActivities = (Button) view.findViewById(R.id.bHomeActivities);
        bActivities.setOnClickListener(new View.OnClickListener() {
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

        Button bFriends = (Button) view.findViewById(R.id.bHomeFriends);
        bFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vFriends = new FriendsListFragment();
                vFriends.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vFriends);
                fragmentTransaction.commit();
            }
        });

        Button bRecordMeal = (Button) view.findViewById(R.id.bHomeRecordMeal);
        bRecordMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment vRecordmeal = new RecordMealFragment();
                vRecordmeal.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_user_area, vRecordmeal);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
