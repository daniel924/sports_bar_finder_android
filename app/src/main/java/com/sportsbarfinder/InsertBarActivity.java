package com.sportsbarfinder;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dan on 11/26/14.
 */
public class InsertBarActivity extends Activity {
    private static String LOG_TAG = InsertBarActivity.class.getSimpleName();

    class InsertTask extends AsyncTask<String, Void, Void> {

        @Override
        public Void doInBackground(String... params) {
            try {
                final String bar = params[0];
                final List<String> teams = Arrays.asList(params[1].split(","));
                final String city = params[2];
                final String address = params[3];
                if(teams.size() < 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    InsertBarActivity.this,
                                    "Need at least one team.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    return null;
                }
                ApiUtils.insertBar(bar, teams, city, address);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                InsertBarActivity.this,
                                "Successfully inserted info.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (IOException ex) {
                Log.d(LOG_TAG, ex.getMessage());
                ex.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bar);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText barText = (EditText) findViewById(R.id.insert_bar_text);
        final EditText teamsText = (EditText) findViewById(R.id.insert_team_text);
        final EditText cityText = (EditText) findViewById(R.id.insert_city_text);
        final EditText addressText = (EditText) findViewById(R.id.insert_address_text);
        Button insertButton = (Button) findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertTask().execute(
                        barText.getText().toString(), teamsText.getText().toString(),
                        cityText.getText().toString(), addressText.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.find_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
