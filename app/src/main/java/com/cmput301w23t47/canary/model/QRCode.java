package com.cmput301w23t47.canary.model;

import android.location.Location;

import java.util.ArrayList;

/**
 * Class for the QR Code
 */
public class QRCode {
    // Hash of QR
    // TODO: Verify type
    private String hash;

    // score of the QR
    private long score;



    ArrayList<Comment> comments;

    // This will be needed for the QRCode to be able to be displayed on the map
    // use the getLatitude and getLongitude methods to get the latitude and longitude
    // this will determine what shoukld go in the MapQRLocationList


    //qr hash
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    //qr score
    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
