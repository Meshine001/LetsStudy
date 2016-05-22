package com.meshine.letsstudyclient.bean;

import java.io.Serializable;

/**
 * Created by Ming on 2016/5/22.
 */
public class Contacts implements Serializable{
    public static final String ID = "_id";
    public static final String USERNAME = "username";
    public static final String CONTACTS = "contacts";

    private String id;
    private String username;
    private String contacts;

    public Contacts() {
    }

    public Contacts(String username, String contacts) {
        this.username = username;
        this.contacts = contacts;
    }

    public Contacts(String id, String username, String contacts) {
        this.id = id;
        this.username = username;
        this.contacts = contacts;
    }

    public static String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
