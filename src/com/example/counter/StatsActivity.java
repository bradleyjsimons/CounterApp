package com.example.counter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
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

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
        {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        Fragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putSerializable(COUNTER_KEY, counterModel);
        args.putInt(StatsFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
        return true;
    }

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
            	Log.e("in the section", "1");
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
