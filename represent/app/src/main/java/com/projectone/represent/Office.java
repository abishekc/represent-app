package com.projectone.represent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Office {
    public Office(String name, String[] levels, int[] indices) {
        this.name = name;
        this.levels = levels;
        this.indices = indices;
    }

    public Office(JSONObject received_office) throws JSONException {
        this.name = received_office.getString("name");
        JSONArray pre_levels = received_office.getJSONArray("levels");
        this.levels = setupLevels(pre_levels);
        this.indices = new int[1];
    }

    private String[] setupLevels(JSONArray pre_levels) {
        String[] levels = new String[pre_levels.length()];
        for(int i = 0; i < pre_levels.length(); i++) {
            try {
                levels[i] = (String) pre_levels.get(i);
            } catch (JSONException e) {
                Log.e("OFFICE", e.getMessage());
            }
        }

        return levels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getLevels() {
        return levels;
    }

    public void setLevels(String[] level) {
        this.levels = level;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    private String name;
    private String levels[];
    private int indices[];
}
