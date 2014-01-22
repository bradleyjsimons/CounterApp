package com.example.counter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCounterActivity extends Activity {
    
        CounterController counterController;
        CounterModel counterModel;
        EditText counterName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_new_counter);
	    // Show the Up button in the action bar.
	    setupActionBar();
	    
	    counterName =  (EditText)findViewById(R.id.counterNameEditText);
	    counterController = new CounterController();
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
		getMenuInflater().inflate(R.menu.add_new_counter, menu);
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
	
	public void clickedCreateButton(View view) {
	    CounterModel counterModel = new CounterModel();
	    counterModel.setCounterName(counterName.getText().toString());
	    counterController.addCounter(counterModel);
	    
	    CharSequence text = "Counter Saved!";
	    int duration = Toast.LENGTH_SHORT;
	    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	    toast.show();
	    
	    this.finish();
	}
	
	public void clickedCancelButton(View view) {
	    CharSequence text = "Counter Not Saved!";
	    int duration = Toast.LENGTH_SHORT;
	    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	    toast.show();
	    
	    this.finish();
	}

}
