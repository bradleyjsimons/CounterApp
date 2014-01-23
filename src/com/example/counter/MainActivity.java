package com.example.counter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.counter.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
	
	public void addNewCounter(View view) {
	    Intent intent = new Intent(this, AddNewCounterActivity.class);
	    startActivity(intent);
	}
	
	public void loadCounter(View view) {
	    Intent intent = new Intent(this, CounterListActivity.class);
	    startActivity(intent);
	}
}