package com.sportsbarfinder;

import java.util.List;

/**
 * Created by Dan on 12/21/14.
 */
public class Bar {
    String name;
    List<String> teams;

    public Bar(String name, List<String> teams) {
        this.name = name;
        this.teams = teams;
    }

    public String getTeams() {
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
