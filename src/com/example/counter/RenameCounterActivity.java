package com.example.counter;

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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class RenameCounterActivity extends Activity {

    private static final String FILENAME = "file.json";
    private CounterModel counterModel;
    private CounterListModel counterListModel;
    private EditText renameEditText;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rename_counter, menu);
        return true;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        this.loadFromFile();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveInFile();
    }

    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
    
    public void cancelRename(View view) {
        this.finish();
    }
    
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

    private void saveInFile() {
        this.deleteContentsOfFile();
        Log.e("length of array", Integer.toString(counterListModel.getCounterList().size()));
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
                Log.e("saving the value",Integer.toString(obj.getCount()));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void deleteContentsOfFile() {
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        boolean deleted = file.delete();
        Log.e("file deleted?", Boolean.toString(deleted));
    }
}
