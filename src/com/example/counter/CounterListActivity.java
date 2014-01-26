package com.example.counter;

import java.io.BufferedReader;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class CounterListActivity extends Activity {

    public final static String EXTRA_MESSAGE = "CounterModelObject";
    private static final String FILENAME = "file.json";
    private CounterListModel counterListModel;
    private ArrayAdapter<CounterModel> adapter;
    private ListView oldCounters;
    private EditText counterNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        setupActionBar();

        oldCounters       = (ListView) findViewById(R.id.counters_list_view);
        counterNameText   = (EditText) findViewById(R.id.add_counter_edit_text);
        Button addButton  = (Button) findViewById(R.id.add_button);
        counterListModel  = new CounterListModel();

        oldCounters.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	CounterModel counterModel = (CounterModel) parent.getAdapter().getItem(position);
            	//String counterName = counterModel.getCounterName();
                Intent intent = new Intent(CounterListActivity.this, CounterActivity.class);
                intent.putExtra(EXTRA_MESSAGE, counterModel);
                startActivity(intent);
            }
        });
        
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                CounterModel counterModel = new CounterModel(counterNameText.getText().toString());
                saveInFile(counterModel);
                adapter.notifyDataSetChanged();
                
                Context context = getApplicationContext();
                CharSequence text = "New Counter Saved!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        
        adapter = new ArrayAdapter<CounterModel>(this, android.R.layout.simple_list_item_1, counterListModel.getCounterList());
        oldCounters.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        this.loadFromFile();
        adapter = new ArrayAdapter<CounterModel>(this,
                android.R.layout.simple_list_item_1, counterListModel.getCounterList());
        oldCounters.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.counter_list, menu);
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
}