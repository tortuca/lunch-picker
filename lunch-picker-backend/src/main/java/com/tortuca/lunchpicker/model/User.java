package com.tortuca.lunchpicker.model;

public class User {
    private final String id;
    private final String username;
    private String password;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
}
