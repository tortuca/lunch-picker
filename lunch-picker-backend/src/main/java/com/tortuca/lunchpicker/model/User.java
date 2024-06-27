package com.tortuca.lunchpicker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String username;
    //    private String password;

    private boolean active;
    private Timestamp createdAt;

    public User() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.active = true;
    }

    public User(String username) {
        this.username = username;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.active = true;
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public Timestamp getCreatedAt() { return this.createdAt; }

    public boolean isActive() { return this.active; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
