package com.sportsbarfinder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FindBarActivity extends Activity {
    private static String LOG_TAG = FindBarActivity.class.getSimpleName();
    ListView listView = null;
    public BarAdapter adapter = null;

    class SearchTask extends AsyncTask<String, Void, List<Bar>> {
        @Override
        public List<Bar> doInBackground(String... params) {
            try {
                final List<Bar> response = ApiUtils.FindBarByName(params[0]);
                if(response == null) {
                    final String notFound = "Could not find results for: " + params[0];
                    FindBarActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setVisibility(View.GONE);
                            TextView notFoundText = (TextView) findViewById(R.id.not_found_txt);
                            notFoundText.setVisibility(View.VISIBLE);
                            notFoundText.setText(notFound);
                        }
                    });
                    return null;
                }

                Log.d(LOG_TAG, "Response: " + response.toString());
                FindBarActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.not_found_txt).setVisibility(View.GONE);
                        adapter.setList(response);
                        listView.setVisibility(View.VISIBLE);
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



    public static class BarFragment extends Fragment {
        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_bar, container, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bar);

        adapter = new BarAdapter(this, new ArrayList<Bar>());
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final SearchView searchView = (SearchView) findViewById(R.id.edit_message);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchTask().execute(searchView.getQuery().toString());
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
        if (adapter != null) {
            // Bar[] values = adapter.getValues();
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state from savedInstanceState
        String bar_result = savedInstanceState.getString("bar_list");
        if(bar_result != null) {
            // barText.setText(bar_result);
            // barText.setVisibility(View.VISIBLE);
        }
    }

    public void clickMapButton(View v) {
        Bar bar = (Bar) v.getTag();
        String query = bar.name;
        if(!bar.address.equals("")) {
            query += ", " + bar.address;
        }
        if(!bar.city.equals("")) {
            query += ", " + bar.city;
        }
        final Uri geoLocation = Uri.parse("geo:0,0?q=" + query);
        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoLocation);
        mapIntent.setPackage("com.google.android.apps.maps");
        FindBarActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mapIntent.resolveActivity(FindBarActivity.this.getPackageManager()) == null) {
                    Toast.makeText(
                            FindBarActivity.this,
                            "You need maps app for this feature.",
                            Toast.LENGTH_SHORT);
                }
                else {
                    FindBarActivity.this.startActivity(mapIntent);
                }
            }
        });
    }

}
