package com.example.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class CounterActivity extends Activity {

	private static final String FILENAME = "file.json";
	CounterModel counterModel;
	CounterListModel counterListModel;
	TextView counterNameTextView;
	TextView currentCountTextView;
	Button resetButton;
	Button incrementButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		setupActionBar();

		Intent intent = getIntent();
		counterModel = (CounterModel) intent.getSerializableExtra("CounterModel_Object");

		counterListModel = new CounterListModel();
		counterNameTextView     = (TextView) findViewById(R.id.counterName);
		currentCountTextView	= (TextView) findViewById(R.id.currentCount);
		resetButton				= (Button)   findViewById(R.id.reset);
		incrementButton   		= (Button)   findViewById(R.id.increment);

		currentCountTextView.setText(Integer.toString(counterModel.getCount()));
		counterNameTextView.setText(counterModel.getCounterName());
		this.loadFromFile();
	}

	public void incrementCount(View view) {
		counterModel.incrementCounter();
		currentCountTextView.setText(Integer.toString(counterModel.getCount()));
	}

	public void resetCount(View view) {
		counterModel.resetCounter();
		currentCountTextView.setText(Integer.toString(counterModel.getCount()));
	}

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

	@Override
	public void onStop() {
		super.onStop();
		saveInFile ();
	}

	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			Gson gson = new Gson();
			while (line != null) {
				CounterModel counter = gson.fromJson(line, CounterModel.class);
				if (counter.getCounterName() == counterModel.getCounterName()) {
					counterListModel.addCounterToList(counterModel);
				} else {
					counterListModel.addCounterToList(counter);
				}
				line = in.readLine();
			}          
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveInFile() {
		//try {
			File f = new File("file.json");
			f.delete();
			/*for (CounterModel obj : counterListModel.getCounterList()) {
				Gson gson = new Gson();
				String json = gson.toJson(obj) +'\n';
				FileOutputStream fos = openFileOutput(FILENAME,
						Context.MODE_APPEND);
				fos.write(json.getBytes());
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
