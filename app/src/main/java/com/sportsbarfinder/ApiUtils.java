package com.sportsbarfinder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Dan on 11/22/14.
 */
public class ApiUtils {


    private static final int CONNECTION_TIMEOUT_MS = 4000;
    private static final int READ_TIMEOUT_MS = 500;
    private static String LOG_TAG = ApiUtils.class.getSimpleName();
    private static String baseUrl = "http://sports-bar-finder.appspot.com/";

    public static String FindBarByName(String searchVal) throws IOException {
        String response = getRequest(baseUrl+"/search?value=" + searchVal);
        return response;
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
        String barArgs = "populate?bar=" + bar + "&teams=";
        for(String team: teams) {
            barArgs += team + ",";
        }
        barArgs = barArgs.substring(0, barArgs.length()-1);
        getRequest(baseUrl + barArgs);
    }

}
