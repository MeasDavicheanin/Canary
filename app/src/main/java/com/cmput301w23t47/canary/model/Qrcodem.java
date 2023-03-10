package com.cmput301w23t47.canary.model;

import com.google.firebase.firestore.GeoPoint;

public class Qrcodem {
    private String qrHash;
    private GeoPoint location;
    private String name;
    private int score;

    public Qrcodem(String qrHash, GeoPoint location, String name, int score) {
        this.qrHash = qrHash;
        this.location = location;
        this.name = name;
        this.score = score;
    }

    public String getQrHash() {
        return qrHash;
    }

    public void setQrHash(String qrHash) {
        this.qrHash = qrHash;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
