/**
 * This is a simple activity class designed for renaming a counter object. 
 * It receives a counterModel object through an intent from its parent activity
 * (last activity on the stack: CounterListAcivity). It then checks to make sure
 * that the new name supplied is not the same as the original, or is not the same
 * as any other existing counter, and finally that it is between 0 and 40 characters in length
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package com.digicount.counter;

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
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.counter.R;
import com.google.gson.Gson;

public class RenameCounterActivity extends Activity {

    private static final String FILENAME = "file.json";
    private CounterModel counterModel;
    private CounterListModel counterListModel;
    private EditText renameEditText;

    /**
     * Creates the activity and receives the intent, where the counterModel
     * object to be operated on is received
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_counter);
        setupActionBar();

        Intent intent = getIntent();
        counterModel = (CounterModel) intent.getSerializableExtra(CounterListActivity.EXTRA_MESSAGE);
        renameEditText = (EditText) findViewById(R.id.rename_edit_text);
        counterListModel = new CounterListModel();
    }

    
    /**
     * creates the options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rename_counter, menu);
        return true;
    }

    /**
     * calls onStart to the super and loads the data from file 
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        this.loadFromFile();
    }

    /**
     * calls super.onPause() and saves all data to file
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.saveInFile();
    }

    /**
     * sets up the action bar
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * attempts to rename the counterModel object. Again, the rename cannot be the same
     * as the current name, it cannot be the same as any other existing name, and must
     * be between 0 and 40 characters in length. A toast is displayed for each result
     * @param view
     */
    public void submitRename(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String newName = renameEditText.getText().toString();

        if (newName.length() >= 40 || newName.length() <= 0) {
            Toast.makeText(context, "The Counter's name must be between 0 and 40 characters!", duration).show();
            renameEditText.setText("");
            return;
        }

        for (CounterModel obj : counterListModel.getCounterList()) {
            if (obj.getCounterName().equals(newName)) {
                Toast.makeText(context, "Counter Already Exists, duplicates not allowed!", duration).show();
                renameEditText.setText("");
                return;
            }
        }

        counterModel.setCounterName(newName);
        Toast.makeText(context, "Counter Renamed!", duration).show();        
        this.finish();
    }
    
    /**
     * Gives the user an opportunity to cancel the rename. Responds to the onClick
     * from the cancel button widget
     * @param view
     */
    public void cancelRename(View view) {
        this.finish();
    }
    
	/**
	 * This method loads all date from file. It uses a bufferedRead inputStream
	 * to read a Json file line by line. It then uses Gson to convert from Json
	 * to Gson, and then Gson to counterModel object. Each line corresponds to
	 * a counterModel Object, and is added to the counterListModel object (which
	 * then adds the object to its counterList Array). If the line corresponds to
	 * the object that was passed through the intent, ignore it and add the 
	 * object from the intent to the array instead
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
        this.deleteContentsOfFile();
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
    private void deleteContentsOfFile() {
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        file.delete();
    }
}
