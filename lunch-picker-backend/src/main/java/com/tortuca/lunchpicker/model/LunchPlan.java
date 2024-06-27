package com.tortuca.lunchpicker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plans")
public class LunchPlan {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String code;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonManagedReference
    @CollectionTable(name = "`plans_venues`", joinColumns = {@JoinColumn(name = "plan_id")})
    @Column(name = "venue")
    private List<String> venues;

    private String choice;
    private Boolean active;

    private UUID userId;
    private final Timestamp createdAt;

    public LunchPlan() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public LunchPlan(UUID id, String code) {
        this.id = id;
        this.code = code;
        this.createdAt = new Timestamp(System.currentTimeMillis());

        this.active = true;
    }

    public UUID getId() {
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}