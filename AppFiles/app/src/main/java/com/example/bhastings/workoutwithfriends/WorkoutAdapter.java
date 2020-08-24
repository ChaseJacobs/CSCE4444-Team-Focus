package com.example.bhastings.workoutwithfriends;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhastings on 11/17/2016.
 */

public class WorkoutAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public WorkoutAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
