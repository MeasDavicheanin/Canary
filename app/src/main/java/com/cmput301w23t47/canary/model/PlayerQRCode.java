package com.cmput301w23t47.canary.model;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

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
    GenWords genWords = new GenWords();
    private final String name=genWords.getWord();
    // Snapshot of the QR
    private Snapshot snapshot;


}

