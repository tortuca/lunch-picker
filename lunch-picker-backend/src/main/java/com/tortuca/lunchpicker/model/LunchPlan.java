package com.tortuca.lunchpicker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class LunchPlan {
    private final String id;
    private final String code;

//    @OneToMany(mappedBy = "pk.order")
    private List<String> venues;
    private String choice;
    private Boolean active;

    public LunchPlan(
            String id,
            String code) {
        this.id = id;
        this.code = code;
        this.venues = new ArrayList<>();
        this.active = true;
        this.choice = null;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public List<String> getVenues() {
        return venues;
    }

    public void setVenues(List<String> venues) {
        this.venues = venues;
    }

    public void addVenue(String venue) {
        this.venues.add(venue);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}