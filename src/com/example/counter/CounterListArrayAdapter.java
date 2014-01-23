package com.example.counter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CounterListArrayAdapter extends BaseAdapter {

	private ArrayList<CounterModel> counterList;
	private Context context;
	private int numberOfItems;
	
	public CounterListArrayAdapter(ArrayList<CounterModel> counterList, Context context) {
		this.counterList = counterList;
		this.context = context;
		this.numberOfItems = counterList.size();
	}

	@Override
	public int getCount() {
		return numberOfItems;
	}

	@Override
	public Object getItem(int position) {
		return counterList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current list item
        final CounterModel counterModel = counterList.get(position);
        // Get the layout for the list item
        final RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.counter_list_item, parent, false);
     
        // Set the text label as defined in our list item
        TextView txtLabel = (TextView) itemLayout.findViewById(R.id.txtLabel);
        txtLabel.setText(counterModel.getCounterName());

        return itemLayout;
    
	}

}
