package com.projectone.represent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Official {
    public Official(Office office, String name, String party, String[] phones, String[] urls, String photoUrl) {
        this.office = office;
        this.name = name;
        this.party = party;
        this.phones = phones;
        this.urls = urls;
        this.photoUrl = photoUrl;
    }

    public Official(JSONObject json_official) throws JSONException {
        this.name = json_official.getString("name");
        if (json_official.has("party")) {
            this.party = json_official.getString("party");
        } else {
            this.party = "Independent";
        }

        if (json_official.has("photoUrl")) {
            this.photoUrl = json_official.getString("photoUrl");
        } else {
            this.photoUrl = "";
        }

        this.urls = new String[10];
        this.office = new Office("hello", new String[10], new int[10]);
        this.phones = new String[10];
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

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private Office office;
    private String name;
    private String party;
    private String phones[];
    private String urls[];
    private String photoUrl;
}
