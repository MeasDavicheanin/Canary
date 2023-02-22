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

    //Location of the QR
    //Location will store the latitude and longitude of the QR
    private Location location;

    ArrayList<Comment> comments;

    // This will be needed for the QRCode to be able to be displayed on the map
    // use the getLatitude and getLongitude methods to get the latitude and longitude
    // this will determine what shoukld go in the MapQRLocationList
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
