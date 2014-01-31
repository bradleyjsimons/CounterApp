/**
 * This activity class is used to display the current counters, their current counts
 * add new counters, and then select a counter for further modification. It does so
 * by using a listView, which is populated by an array that contains all the counters
 * (counterListModel). At the bottom of the screen, it allows users to add new counters
 * The listView triggers a contextual Menu when a counter item in the list is clicked
 * and held. This menu supports, renaming, resetting, deleting, seeing stats, and sorting
 * the current counters. 
 * 
 * @author Brad Simons
 * @date January 30th, 2014
 */

package com.digicount.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.counter.R;
import com.google.gson.Gson;

public class CounterListActivity extends Activity {

	public final static String EXTRA_MESSAGE = "CounterModelObject";
	private static final String FILENAME = "file.json";
	private CounterListModel counterListModel;
	private CounterListArrayAdapter adapter;
	private ListView lv;
	private EditText counterNameText;

	/**
	 * Creates the activity. The widgets are setup and pointed to
	 * as class attributes. These include the listView, the new counterName
	 * editText widget, the addCounter button widget. A contextMenu is registered
	 * with the listView. Two onClickListeners are set. And finally a custom arrayAdapter
	 * is set in the lisView, which fills the listView with a custom layout displaying
	 * a counterModel object name, followed by its count. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		setupActionBar();

		lv = (ListView) findViewById(R.id.counters_list_view);
		counterNameText = (EditText) findViewById(R.id.add_counter_edit_text);
		Button addButton = (Button) findViewById(R.id.add_button);
		counterListModel = new CounterListModel();
		final Activity activity = this;

		registerForContextMenu(lv);

		/**
		 * this listener is triggered when an item in the listView is clicked on.
		 * It packs up that item (counterModel object) in an intent, and ships it
		 * off to the counterActivity for easy counting.
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				CounterModel counterModel = (CounterModel) parent.getAdapter().getItem(position);
				Intent intent = new Intent(CounterListActivity.this, CounterActivity.class);
				intent.putExtra(EXTRA_MESSAGE, counterModel);
				startActivity(intent);
			}
		});

		/**
		 * this listener is triggered when the add counter button is pressed. This takes
		 * the text in the addCounterEditText and creates a new CounterModel object with it
		 * However, the name must be between 0 and 40 characters in length, and it cannot 
		 * have the same name as an existing counter. 
		 */
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String counterName = counterNameText.getText().toString();
				CounterModel counterModel = new CounterModel(counterName);

				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;

				if (counterName.length() >= 40 || counterName.length() <= 0) {
					Toast.makeText(context, "The Counter's name must be between 0 and 40 characters!", duration).show();
					counterNameText.setText("");
					return;
				}
				for (CounterModel obj : counterListModel.getCounterList()) {
					if (obj.getCounterName().equals(counterModel.getCounterName())) {
						Toast.makeText(context, "Counter Already Exists, duplicates not allowed!", duration).show();
						counterNameText.setText("");
						return;
					}
				}
				saveInFile(counterModel);
				adapter = new CounterListArrayAdapter(activity, R.id.counters_list_view, counterListModel.getCounterList());
				lv.setAdapter(adapter);

				Toast.makeText(context, "New Counter Created!", duration).show();
				counterNameText.setText("");
			}
		});

		adapter = new CounterListArrayAdapter(this, R.id.counters_list_view, counterListModel.getCounterList());
		lv.setAdapter(adapter);
	}

	/**
	 * setups up the ContextMenu. Sets the header title and adds 6 options
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Select an option");
		menu.add("Reset");
		menu.add("Rename");  
		menu.add("Delete");
		menu.add("Stats");
		menu.add("Sort (All Counters)");
		menu.add("Cancel");
	}

	/**
	 * This model deals with menu items being selected from the Context Menu
	 * It can do anything from resetting a counter, to going to the stats activity
	 * to deleting the counter, and sorting the collection of all counters. 
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		CounterModel selectedCounter = adapter.getItem(info.position);

		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		if (item.getTitle().equals("Delete")) {
			Toast.makeText(context, "Counter Deleted!", duration).show();
			this.deleteCounterModel(selectedCounter);
		}

		if (item.getTitle().equals("Reset")) {
			Toast.makeText(context, "Counter Reset!", duration).show();
			this.resetCounter(selectedCounter);
		}

		if (item.getTitle().equals("Rename")) {
			Intent intent = new Intent(CounterListActivity.this, RenameCounterActivity.class);
			intent.putExtra(EXTRA_MESSAGE, selectedCounter);
			startActivity(intent);
		}

		if (item.getTitle().equals("Sort (All Counters)")) {
			counterListModel.sortCounterList();
			adapter = new CounterListArrayAdapter(this, R.id.counters_list_view, counterListModel.getCounterList());
			lv.setAdapter(adapter);
			this.saveChangedArrayToFile();
		}

		if (item.getTitle().equals("Stats")) {
			Intent intent = new Intent(CounterListActivity.this, StatsActivity.class);
			intent.putExtra(EXTRA_MESSAGE, selectedCounter);
			startActivity(intent);
		}

		return true;
	}

	/**
	 * The superclass onStart() is called, a new counterListModel is created, 
	 * and we call loadFromFile to fill it. Then, the arrayAdapter is set again to 
	 * fill the listView with counterModel objects (in the same format as above)
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		counterListModel = new CounterListModel();
		this.loadFromFile();
		adapter = new CounterListArrayAdapter(this, R.id.counters_list_view, counterListModel.getCounterList());
		lv.setAdapter(adapter);
	}

	/**
	 * sets up the action bar
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Inflate the menu; adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_list, menu);
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
	private void loadFromFile() {;
	try {
		FileInputStream fis = openFileInput(FILENAME);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		String line = in.readLine();
		Gson gson = new Gson();
		while (line != null) {
			CounterModel counterModel = gson.fromJson(line, CounterModel.class);
			counterListModel.addCounterToList(counterModel);
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
	 * This method stores all counterData. It takes a new counterModel object
	 * as an arg, and appends it to the file as new line Json string. 
	 */
	private void saveInFile(CounterModel counterModel) {
		try {
			counterListModel.addCounterToList(counterModel);
			Gson gson = new Gson();
			String json = gson.toJson(counterModel) +'\n';
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

	/**
	 * deletes a single counter from counterListModel. This object will then not be saved
	 * and is garbage collected when it goes out of scope. 
	 * @param counterToBeDeleted
	 */
	private void deleteCounterModel(CounterModel counterToBeDeleted) {
		ArrayList<CounterModel> newCounterList = new ArrayList<CounterModel>();
		for (CounterModel obj : counterListModel.getCounterList()) {
			if (!(obj.getCounterName().equals(counterToBeDeleted.getCounterName()))) {
				newCounterList.add(obj);
			}
		}
		counterListModel.setCounterList(newCounterList);
		adapter = new CounterListArrayAdapter(this, R.id.counters_list_view, counterListModel.getCounterList());
		lv.setAdapter(adapter);
		this.saveChangedArrayToFile();
	}

	/**
	 * resets the counterModel passed in as an arg by calling it's own method to reset
	 * The updates the arrayAdapter using notifyDataSetChanged.
	 * @param counterModel
	 */
	private void resetCounter(CounterModel counterModel) {
		counterModel.resetCounter();
		adapter = new CounterListArrayAdapter(this, R.id.counters_list_view, counterListModel.getCounterList());
		lv.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		saveChangedArrayToFile();
	}

	/**
	 * This method stores all counterData when changed. It first deletes the json file 
	 * "file.json" used to store. It then takes every counterModel object in the counterListModel,
	 * serialize's it as a Gson to Json, and appends it line by line to the file.json. 
	 */
	private void saveChangedArrayToFile() {
		this.deleteContentsOfFile();
		for (CounterModel obj : counterListModel.getCounterList()) {
			try {
				Gson gson = new Gson();
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
	private void deleteContentsOfFile() {
		File dir = getFilesDir();
		File file = new File(dir, FILENAME);
		file.delete();
	}
}