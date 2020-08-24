package com.example.bhastings.workoutwithfriends;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealSubmissionConformationFragment extends Fragment {

    String username;
    Bundle bundle = new Bundle();

    public MealSubmissionConformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = this.getArguments().getString("username");
        bundle.putString("username", username);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_submission_conformation, container, false);
    }

}
