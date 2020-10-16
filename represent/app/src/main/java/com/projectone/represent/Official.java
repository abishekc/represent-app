package com.projectone.represent;

public class Official {
    public Official(Office office, String name, String party, String[] phones, String[] urls, String photoUrl) {
        this.office = office;
        this.name = name;
        this.party = party;
        this.phones = phones;
        this.urls = urls;
        this.photoUrl = photoUrl;
    }

    public Official() {

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
