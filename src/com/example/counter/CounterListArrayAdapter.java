package com.example.counter;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;


public class CounterListArrayAdapter extends ArrayAdapter<CounterModel> {
    
    public CounterListArrayAdapter(Context context, int resource,
            int textViewResourceId, ArrayList<CounterModel> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
