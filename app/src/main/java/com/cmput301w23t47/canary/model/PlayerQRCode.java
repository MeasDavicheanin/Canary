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

    //Location of the QR stored in latitude and longitude
    private Location location;


    // name of the QR
    private String name;
    // Snapshot of the QR
    private Snapshot snapshot;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public QRCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QRCode qrCode) {
        this.qrCode = qrCode;
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

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }
}
