package com.example.counter;

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
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
    private ListView lv;
    private EditText counterNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        setupActionBar();

        lv = (ListView) findViewById(R.id.counters_list_view);
        counterNameText = (EditText) findViewById(R.id.add_counter_edit_text);
        Button addButton = (Button) findViewById(R.id.add_button);
        counterListModel = new CounterListModel();

        registerForContextMenu(lv);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                CounterModel counterModel = new CounterModel(counterNameText.getText().toString());

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if (counterModel.getCounterName().length() >= 40 || counterModel.getCounterName().length() <= 0) {
                    Toast.makeText(context, "Name must be between 0 and 40 characters!", duration).show();
                    return;
                }

                for (CounterModel obj : counterListModel.getCounterList()) {
                    if (obj.getCounterName().equals(counterModel.getCounterName())) {
                        Toast.makeText(context, "Counter Already Exists!", duration).show();
                        return;
                    }
                }

                saveInFile(counterModel);
                adapter.notifyDataSetChanged();

                Toast.makeText(context, "New Counter Created!", duration).show();
            }
        });

        adapter = new ArrayAdapter<CounterModel>(this, android.R.layout.simple_list_item_1, counterListModel.getCounterList());
        lv.setAdapter(adapter);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {

        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add("Delete");
        menu.add("Rename");   
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        super.onContextItemSelected(item);

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        CounterModel selectedCounter = adapter.getItem(info.position);

        if (item.getTitle().equals("Delete")) {
            deleteCounterModel(selectedCounter);
        }

        if (item.getTitle().equals("Rename")) {
            Log.e("trying to rename", "this item");
        }

        return true;
    }



    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        this.loadFromFile();
        adapter = new ArrayAdapter<CounterModel>(this,
                android.R.layout.simple_list_item_1, counterListModel.getCounterList());
        lv.setAdapter(adapter);
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
            Log.e("loaded the value", Integer.toString(counterModel.getCount()));
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
        Log.e("size of counter list",Integer.toString(counterListModel.getCounterList().size()));
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

    private void deleteCounterModel(CounterModel counterToBeDeleted) {
        ArrayList<CounterModel> newCounterList = new ArrayList<CounterModel>();
        Log.e("size of list", Integer.toString(counterListModel.getCounterList().size()));
        for (CounterModel obj : counterListModel.getCounterList()) {
            if (!(obj.getCounterName().equals(counterToBeDeleted.getCounterName()))) {
                newCounterList.add(obj);
            }
        }
        counterListModel.setCounterList(newCounterList);
        adapter = new ArrayAdapter<CounterModel>(this,
                android.R.layout.simple_list_item_1, counterListModel.getCounterList());
        lv.setAdapter(adapter);
        this.saveChangedArrayToFile();
    }

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