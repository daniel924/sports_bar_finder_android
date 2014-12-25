package com.sportsbarfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;


public class FindBarActivity extends Activity {
    private static String LOG_TAG = FindBarActivity.class.getSimpleName();
    private TextView barText = null;
    private TextView teamsText = null;

    class SearchTask extends AsyncTask<String, Void, Bar> {

        @Override
        public Bar doInBackground(String... params) {
            try {
                final Bar response = ApiUtils.FindBarByName(params[0]);
                Log.d(LOG_TAG, "Response: " + response.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        barText.setText(response.name);
                        barText.setVisibility(View.VISIBLE);
                        teamsText.setText(response.getTeams());
                        teamsText.setVisibility(View.VISIBLE);
                    }
                });
                return response;
            }
            catch (IOException ex) {
                Log.d(LOG_TAG, ex.getMessage());
                ex.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bar);

        barText = (TextView) findViewById(R.id.bar_result);
        teamsText = (TextView) findViewById(R.id.teams_result);
        final EditText editText = (EditText) findViewById(R.id.edit_message);
        final String barName = editText.getText().toString();

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchTask().execute(editText.getText().toString());
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_insert) {
            Intent intent = new Intent(this, InsertBarActivity.class);
            // EditText editText = (EditText) findViewById(R.id.edit_message);
            // String message = editText.getText().toString();
            // intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save state to the savedInstanceState
        if (barText != null) {
            savedInstanceState.putString("bar_result", (String) barText.getText());
        }
        if(teamsText != null) {
            savedInstanceState.putString("teams_result", (String) teamsText.getText());
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state from savedInstanceState
        String bar_result = savedInstanceState.getString("bar_result");
        if(bar_result != null) {
            barText.setText(bar_result);
            barText.setVisibility(View.VISIBLE);
        }

    }


}
