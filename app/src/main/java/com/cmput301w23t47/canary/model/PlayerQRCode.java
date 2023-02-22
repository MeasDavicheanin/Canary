package com.cmput301w23t47.canary.model;

import android.location.Location;

/**
 * Class for the QR Code scanned by the Player
 */
public class PlayerQRCode {
    // Player who scanned the QR
    private Player player;
    // QR Code scanned
    private QRCode qrCode;

    //I think this should be stored in the qrcode class
    //Location of the QR
    private Location location;


    // name of the QR
    private String name;
    // Snapshot of the QR
    private Snapshot snapshot;
}
