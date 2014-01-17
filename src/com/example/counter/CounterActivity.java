package com.example.counter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity {

	Counter counter;
	TextView counterNameTextView;
	TextView currentCountTextView;
	Button resetButton;
	Button incrementButton;
	int currentCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		// Show the Up button in the action bar.
		setupActionBar();
		
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		counterNameTextView     = (TextView) findViewById(R.id.counterName);
		currentCountTextView	= (TextView) findViewById(R.id.currentCount);
		resetButton				= (Button)   findViewById(R.id.reset);
		incrementButton   		= (Button)   findViewById(R.id.increment);
		
		currentCountTextView.setText(Integer.toString(currentCount));
		counter.setCounterName(message);
		counterNameTextView.setText(message);
	}

	public void incrementCount(View view) {
		counter.incrementCounter();
		currentCountTextView.setText(Integer.toString(counter.getCount()));
	}
	
	public void resetCount(View view) {
		counter.resetCounter();
		currentCountTextView.setText("0");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.count, menu);
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

}
