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
}
