package com.cmput301w23t47.canary.model;

import android.location.Location;

import java.util.ArrayList;

/**
 * Class for the QR Code
 */
public class QrCode {
    // Hash of QR
    private String hash;
    // score of the QR
    private long score;
    // Location of the QR; Stores the last known location
    private Location location;
    // name of the QR
    private String name;
    ArrayList<Comment> comments;

    public QrCode(String hash, long score, Location location, String name) {
        this.hash = hash;
        this.score = score;
        this.location = location;
        this.name = name;
    }

    public QrCode() {}

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
