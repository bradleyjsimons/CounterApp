/**
 * This class implements a customArrayAdaptor which is used to display the 
 * counterModel objects in the listView of the CounterListActivity. It uses the 
 * counter_list_item.xml file to create a layout which takes each counterModel objects
 * name and count, and puts them in a view of its cell in the table. 
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package ca.ualberta.ca.simons_counter;

import java.util.ArrayList;

import ca.ualberta.cs.simons_digicount.R;

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
    
    /**
     * constructor
     * @param activity
     * @param textViewResourceId
     * @param entries
     */
    public CounterListArrayAdapter(Activity activity, int textViewResourceId, ArrayList<CounterModel> entries) {
        super(activity, textViewResourceId, entries);
        this.entries = entries;
        this.activity = activity;
    }
    
    /**
     * This class is essentially a wrapper to hold the counterModel's name and count
     * @author bradsimons
     */
    public static class ViewHolder {
        public TextView name;
        public TextView count;
    }
    
    /**
     * This is where the view is implemented and returned. We use the ViewHolder class
     * created above and the xml file to inflate the view. We then set the ViewHolder (holder)
     * attribute textView's to the appropriate counterName or count. 
     */
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
