/**
 * This activity class is used to display the statistics for a particular
 * counterModel object. It using a drop down menu to select if we want to display
 * statistics based on hourly, daily, weekly or monthly periods. By implementing the
 * drop down menu, fragment activities are created. Bundle objects are used to pass arguments
 * to these fragments. Each fragment has a listView and displays the statistics in a simple
 * listView table type format. 
 */

package com.digicount.counter;

import java.util.ArrayList;

import com.example.counter.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StatsActivity extends FragmentActivity implements
        ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final String COUNTER_KEY = "counter_key";
    private CounterModel counterModel;
    
    /**
     * Creates activity, and receives the counterModel object from the intent. Sets up the
     * Action Bar for the drop down menu, and creates four options in that menu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        counterModel = (CounterModel) intent.getSerializableExtra(CounterListActivity.EXTRA_MESSAGE);
        
        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        // Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
        // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, new String[] {
                                getString(R.string.counts_per_hour_title),
                                getString(R.string.counts_per_day_title),
                                getString(R.string.counts_per_week_title),
                                getString(R.string.counts_per_month_title),}), this);
    }

    /**
     * Restores the previously serialized current dropdown position.
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
        {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    /**
     *  Serializes the current dropdown position
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    /**
     * Inflates the menu; adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stats, menu);
        return true;
    }

    /**
	 * Implements a up button to navigate one level up in the 
	 * app structure
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * shows the view from the current dropdown selection by creating a fragment
     * and passing the arguments in a bundle to that fragment. 
     */
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        Fragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putSerializable(COUNTER_KEY, counterModel);
        args.putInt(StatsFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
        return true;
    }

    /**
     * This is the fragment class. It displays the results of the statistical
     * inference in a listView format. It checks which arg section number is in
     * and acts accordingly (by hour, day, week or month) by setting the array
     * adapter with the returned string (again by hour, day, week or month)
     */
    public static class StatsFragment extends Fragment {
        
        public static final String ARG_SECTION_NUMBER = "section_number";
        
        public StatsFragment() {
        }
       
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_stats,
                    container, false);
            ListView lv = (ListView) rootView.findViewById(R.id.listView);
            CounterModel fragCounterModel = (CounterModel) getArguments().getSerializable(COUNTER_KEY);
            int sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            
            ArrayList<String> dateList;
            if (sectionNum == 1) {
            	dateList = fragCounterModel.getStatsModel().getHourStrings();
            }
            else if (sectionNum == 2) {
            	dateList = fragCounterModel.getStatsModel().getDayStrings();
            }
            else if (sectionNum == 3) {
            	dateList = fragCounterModel.getStatsModel().getWeekStrings();
            } 
            else {
            	dateList = fragCounterModel.getStatsModel().getMonthStrings();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dateList);
            lv.setAdapter(adapter);
            return rootView;
        }
    }
}
