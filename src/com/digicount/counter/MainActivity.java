/**
 * This activity class is a very simple class that displays a welcome screen. 
 * It responds to a onClick event from the "Get Started" button widget and 
 * moves to the CounterListActivity
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package com.digicount.counter;

import com.example.counter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	/**
	 * Creates the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	}

	/**
	 * creates the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
	
	/**
	 * responds to the onClick event from the get started button widget
	 * and initiates transition to the next activity (CounterListAcivity)
	 * @param view
	 */
	public void loadListViewActivity(View view) {
	    Intent intent = new Intent(this, CounterListActivity.class);
	    startActivity(intent);
	}
}