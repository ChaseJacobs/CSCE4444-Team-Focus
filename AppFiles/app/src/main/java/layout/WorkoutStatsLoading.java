package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhastings.workoutwithfriends.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutStatsLoading extends Fragment {

    Bundle bundle = new Bundle();
    String username;

    public WorkoutStatsLoading() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_stats_loading, container, false);




        return view;
    }

}
