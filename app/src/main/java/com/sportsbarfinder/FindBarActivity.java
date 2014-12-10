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
    private TextView resultText;

    class SearchTask extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String... params) {
            try {
                final String response = ApiUtils.FindBarByName(params[0]);
                Log.d(LOG_TAG, "Response: " + response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultText.setText(response);
                        resultText.setVisibility(View.VISIBLE);
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

        resultText = (TextView) findViewById(R.id.result_message);
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
}
