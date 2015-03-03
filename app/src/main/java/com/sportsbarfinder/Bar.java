package com.sportsbarfinder;

import java.util.List;

/**
 * Created by Dan on 12/21/14.
 */
public class Bar {
    String name;
    List<String> teams;
    String city;
    String address;

    public Bar(String name, List<String> teams, String city, String address) {
        this.name = name;
        this.teams = teams;
        this.city = city;
        this.address = address;
    }

    public String prettyPrintTeams() {
        String s = "";
        for(String team: teams) {
            s += team + "\n";
        }
        return s;
    }

    public String toString() {
        return name + "\n" + teams.toString();
    }
}
