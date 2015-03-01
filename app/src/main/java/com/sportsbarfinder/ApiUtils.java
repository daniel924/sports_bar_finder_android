package com.sportsbarfinder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 11/22/14.
 */
public class ApiUtils {


    private static final int CONNECTION_TIMEOUT_MS = 4000;
    private static final int READ_TIMEOUT_MS = 500;
    private static String LOG_TAG = ApiUtils.class.getSimpleName();
    private static String baseUrl = "http://sports-bar-finder.appspot.com/";

    public static Bar FindBarByName(String searchVal) throws IOException {
        String response = getRequest(
                baseUrl + "/search?value=" + URLEncoder.encode(searchVal, "UTF-8"));
        if (response.equals("")) {
            return null;
        }
        try {
            JSONArray barList = new JSONObject(response).getJSONArray("bars");
            Bar[] bars = new Bar[barList.length()];

            for(int i = 0; i < barList.length(); i++) {
                JSONObject o = barList.getJSONObject(i);
                String name = o.getString("name");
                JSONArray jsonTeams = o.getJSONArray("teams");
                int x = jsonTeams.length();
                List<String> teams = new ArrayList<String>();
                for(int j = 0; j < jsonTeams.length(); j++ ) {
                    teams.add(jsonTeams.getString(j));
                }
                bars[i] = new Bar(name, teams);
            }
            return bars[0];
        } catch (JSONException ex) {
            // Do something better here, maybe even throw the exception.
            return null;
        }
    }

    private static String getRequest(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setConnectTimeout(CONNECTION_TIMEOUT_MS);
        conn.setReadTimeout(READ_TIMEOUT_MS);
        InputStream inputStream = conn.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null ) {
            result.append(line);
        }
        return result.toString();
    }

    public static void insertBar(String bar, List<String> teams) throws IOException {
        String barArgs = "populate?bar=" + URLEncoder.encode(bar, "UTF-8") + "&teams=";
        for(String team: teams) {
            barArgs += URLEncoder.encode(team, "UTF-8") + ",";
        }
        barArgs = barArgs.substring(0, barArgs.length()-1);
        getRequest(baseUrl + barArgs);
    }

}
