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


/**
 * A simple {@link Fragment} subclass.
 */
public class NewExerciseFragment extends Fragment {


    public NewExerciseFragment() {
        // Required empty public constructor
    }

    //check to see if fields are empty
    public boolean isEmpty(EditText etText){
        return etText.getText().toString().length() == 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_exercise, container, false);

     //XML Declarations
        //(insert image view declaration here)
        final EditText exerciseName = (EditText) view.findViewById(R.id.etExerciseName);
        final Spinner exerciseType = (Spinner) view.findViewById(R.id.sExerciseType);
        final EditText exerciseTime = (EditText) view.findViewById(R.id.etExerciseTime);
        final EditText exerciseDistance = (EditText) view.findViewById(R.id.etExerciseDistance);
        final Spinner exerciseMuscleGroup = (Spinner) view.findViewById(R.id.sExerciseMuscleGroup);
        final EditText exerciseWeight = (EditText) view.findViewById(R.id.etExerciseWeight);

        Button bSaveExercise = (Button) view.findViewById(R.id.bSaveNewExercise);
        bSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //test to make sure fields are not null
                if (isEmpty(exerciseName)){
                    //display error message
                    Toast.makeText(getActivity(), "You did not enter a name", Toast.LENGTH_SHORT).show();
                }
                else if (isEmpty(exerciseTime)){
                    //display error message
                    Toast.makeText(getActivity(), "You did not enter a time", Toast.LENGTH_SHORT).show();
                }
                else if (isEmpty(exerciseDistance)){
                    //display error message
                    Toast.makeText(getActivity(), "You did not enter a distance", Toast.LENGTH_SHORT).show();
                }
                else if(isEmpty(exerciseWeight)){
                    //display error message
                    Toast.makeText(getActivity(), "You did not enter a weight", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Store values from fields entered by user
                    //(insert image view value here)
                     String name = (String) exerciseName.getText().toString();
                     String type = (String) exerciseType.getSelectedItem().toString();
                     int time = Integer.parseInt(exerciseTime.getText().toString());
                     int distance = Integer.parseInt(exerciseDistance.getText().toString());
                     String muscleGroup = (String) exerciseMuscleGroup.getSelectedItem().toString();
                     int weight = Integer.parseInt(exerciseWeight.getText().toString());

                     //send data to database

                     //go back to Activities Screen
                     Fragment vActivityScreen = new ActivitiesFragment();
                     FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.content_user_area, vActivityScreen);
                     fragmentTransaction.commit();
                }

            }
        });

        return view;
    }

}
