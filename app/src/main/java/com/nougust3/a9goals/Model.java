package com.nougust3.a9goals;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

class Model {
    private Context context;

    private ArrayList<Goal> goals;
    
    Model(Context context) {
        this.context = context;
        goals = new ArrayList<>();
        loadGoals();
    }

    ArrayList<Goal> getGoals() {
        return goals;
    }

    private void sort() {
        Collections.sort(goals, new Comparator<Goal>(){
            @Override
            public int compare(Goal goal1, Goal goal2) {
                return ((Integer)goal1.getType()).compareTo(goal2.getType());
            }
        });
        saveGoals();
    }

    int getGoalsCount() {
        return goals.size();
    }

    void appendGoal(String goal, int type) {
        Goal newGoal = new Goal(goal, type, false);
        goals.add(newGoal);
        sort();
        saveGoals();
    }

    void remove(int id) {
        goals.remove(id);
        saveGoals();
    }

    void loadGoals() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int size = preferences.getInt("Goals_size2", 0);

        if (size == 0) {
            return;
        }

        goals.clear();

        for (int i = 0; i < size; i++) {
            Goal goal= new Goal(preferences.getString("Name2" + i, null),preferences.getInt("Type2" + i, 1), preferences.getBoolean("State2" + i, false));
            goals.add(goal);
        }
    }

    void saveGoals() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editior = preferences.edit();

        for (int i = 0; i < goals.size(); i++) {
            editior.remove("Name2" + i);
            editior.putString("Name2" + i, goals.get(i).getName());
            editior.remove("Type2" + i);
            editior.putInt("Type2" + i, goals.get(i).getType());
            editior.remove("State2" + i);
            editior.putBoolean("State2" + i, goals.get(i).getState());
        }

        editior.remove("Goals_size2");
        editior.putInt("Goals_size2", goals.size());

        editior.apply();
    }
}
