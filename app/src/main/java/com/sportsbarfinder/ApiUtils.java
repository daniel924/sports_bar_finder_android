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

    public static List<Bar> FindBarByName(String searchVal) throws IOException {
        String response = getRequest(
                baseUrl + "/search?value=" + URLEncoder.encode(searchVal, "UTF-8"));
        if (response.equals("")) {
            return null;
        }
        try {
            JSONArray barList = new JSONObject(response).getJSONArray("bars");
            List<Bar> bars = new ArrayList<Bar>();

            for(int i = 0; i < barList.length(); i++) {
                JSONObject o = barList.getJSONObject(i);
                JSONArray jsonTeams = o.getJSONArray("teams");
                List<String> teams = new ArrayList<String>();
                for(int j = 0; j < jsonTeams.length(); j++ ) {
                    teams.add(jsonTeams.getString(j));
                }
                bars.add(new Bar(
                        o.getString("name"), teams,
                        o.getString("city"), o.getString("address")));
            }
            return bars;
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

    public static void insertBar(String bar, List<String> teams, String city, String address) throws IOException {
        String insertArgs = "insert?";
        insertArgs += "bar=" + URLEncoder.encode(bar, "UTF-8");
        insertArgs += "&city=" + URLEncoder.encode(city, "UTF-8");
        insertArgs += "&address" + URLEncoder.encode(address, "UTF-8");
        insertArgs += "&teams=";
        for(String team: teams) {
            insertArgs += URLEncoder.encode(team, "UTF-8") + ",";
        }
        insertArgs = insertArgs.substring(0, insertArgs.length()-1);
        getRequest(baseUrl + insertArgs);
    }

}
