/**
 * This activity class implements the layout where the actual counting begins
 * It has file I/O capibility, and implements persistent data by always storing
 * data on the onPause() method. It has two Buttons, one to reset a count and
 * another to increment the count. 
 * 
 * @author Brad Simons
 * @date January 30th, 2014
 */

package ca.ualberta.ca.simons_counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.simons_digicount.R;
import com.google.gson.Gson;

public class CounterActivity extends Activity {

	private static final String FILENAME = "file.json";
	private CounterModel counterModel;
	private CounterListModel counterListModel;
	private TextView counterNameTextView;
	private TextView currentCountTextView;

	/**
	 * Creates an instance of this activity. Receives an intent
	 * from the previous activity, which contains the counterModel
	 * object which will be modified in this activity. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		setupActionBar();

		Intent intent = getIntent();
		counterModel = (CounterModel) intent.getSerializableExtra(CounterListActivity.EXTRA_MESSAGE);

		counterNameTextView = (TextView) findViewById(R.id.counterName);
		currentCountTextView = (TextView) findViewById(R.id.currentCount);

		counterListModel = new CounterListModel();
		counterNameTextView.setText(counterModel.getCounterName());
	}

	/**
	 * This method calls onStart() to its superclass, and also
	 * loads all the data from file (all the stored Counter information)
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.loadFromFile();
	}
	
	/**
	 * calls onPause() in the super class, and stores all data to file
	 */
	@Override
	protected void onPause() {
	    super.onPause();
	    this.saveInFile();
	}

	/**
	 * increments the counterModel object. This occurs when the increment 
	 * Button widget is pressed. It then updates the currentCountTextView to
	 * display the updated count. 
	 * @param view
	 */
	public void incrementCount(View view) {
		counterModel.incrementCounter();
		currentCountTextView.setText(Integer.toString(counterModel.getCount()));
	}

	/**
	 * simply resets the counterModel to count 0. It also updates the textView
	 * displaying the count to 0. This method is called when the reset Button 
	 * widget is pressed. 
	 * @param view
	 */
	public void resetCount(View view) {
		counterModel.resetCounter();
		currentCountTextView.setText(Integer.toString(counterModel.getCount()));
	}

	/**
	 * sets up the action bar
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * inflates the menu and adds items to action bar if present
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.count, menu);
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
	 * This method loads all date from file. It uses a bufferedRead inputStream
	 * to read a Json file line by line. It then uses Gson to convert from Json
	 * to Gson, and then Gson to counterModel object. Each line corresponds to
	 * a counterModel Object, and is added to the counterListModel object (which
	 * then adds the object to its counterList Array).
	 */
	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			Gson gson = new Gson();
			while (line != null) {
				CounterModel counter = gson.fromJson(line, CounterModel.class);
				if (counterModel.getCounterName().equals(counter.getCounterName())) {
					counterListModel.addCounterToList(counterModel);
					currentCountTextView.setText(Integer.toString(counterModel.getCount()));
				} else {
					counterListModel.addCounterToList(counter);
				}
				line = in.readLine();
			}          
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method stores all counterData. It first deletes the json file "file.json"
	 * used to store. It then takes every counterModel object in the counterListModel,
	 * serialize's it as a Gson to Json, and appends it line by line to the file.json. 
	 */
	private void saveInFile() {
		this.deleteFile();
		for (CounterModel obj : counterListModel.getCounterList()) {
			try {
				Gson gson = new Gson();
				if (obj.getCounterName().equals(counterModel.getCounterName())) {
					obj = counterModel;
				}
				String json = gson.toJson(obj) +'\n';
				FileOutputStream fos = openFileOutput(FILENAME,
						Context.MODE_APPEND);
				fos.write(json.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Simply deletes the json file "file.json". 
	 */
	private void deleteFile() {
		File dir = getFilesDir();
		File file = new File(dir, FILENAME);
		file.delete();
	}

}