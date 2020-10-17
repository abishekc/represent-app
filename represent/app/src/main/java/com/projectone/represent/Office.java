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

    public Office(JSONObject json_office) throws JSONException {
        //Name
        this.name = json_office.getString("name");

        //Levels
        JSONArray pre_levels = json_office.getJSONArray("levels");
        this.levels = setupLevels(pre_levels);

        //Officials Indices
        JSONArray pre_indices = json_office.getJSONArray("officialIndices");
        this.indices = setupIndices(pre_indices);
    }
    private int[] setupIndices(JSONArray pre_indices) {
        int[] indices = new int[pre_indices.length()];
        for (int i = 0; i < pre_indices.length(); i++) {
            try {
                indices[i] = (int) pre_indices.get(i);
            } catch (JSONException e) {
                Log.e("OFFICE", e.getMessage());
            }
        }

        return indices;
    }

    private String[] setupLevels(JSONArray pre_levels) {
        String[] levels = new String[pre_levels.length()];
        for (int i = 0; i < pre_levels.length(); i++) {
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
