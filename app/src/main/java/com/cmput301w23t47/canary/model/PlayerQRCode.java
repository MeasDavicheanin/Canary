package com.cmput301w23t47.canary.model;

/**
 * Class for the QR Code scanned by the Player
 */
public class PlayerQRCode {
    // Player who scanned the QR
    private Player player;
    // QR Code scanned
    private QRCode qrCode;
    // Location of the QR
    private Location location;
    // name of the QR
    private String name;
    // Snapshot of the QR
    private Snapshot snapshot;
}
