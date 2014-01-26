package com.example.counter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CounterListArrayAdapter extends ArrayAdapter<CounterModel> {

    private ArrayList<CounterModel> entries;
    private Activity activity;
    
    public CounterListArrayAdapter(Activity activity, int textViewResourceId, ArrayList<CounterModel> entries) {
        super(activity, textViewResourceId, entries);
        this.entries = entries;
        this.activity = activity;
    }
    
    public static class ViewHolder {
        public TextView name;
        public TextView count;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.counter_list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.name_text);
            holder.count = (TextView) v.findViewById(R.id.count_text);
        } else {
            holder = (ViewHolder)v.getTag();
        }
        
        final CounterModel counterModel = entries.get(position);
        if (counterModel != null) {
            holder.name.setText(counterModel.getCounterName());
            holder.count.setText(Integer.toString(counterModel.getCount()));
        }
        return v;
    }

}
