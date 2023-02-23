package com.cmput301w23t47.canary.repository;

import android.location.Location;

import androidx.annotation.NonNull;

import com.cmput301w23t47.canary.model.QrCode;
import com.google.firebase.firestore.GeoPoint;

import java.util.Locale;

public class QrCodeRepository {
    // Hash of QR
    private String hash;
    // score of the QR
    private long score;
    // Location of the QR; Stores the last known location
    private GeoPoint location;
    // name of the QR
    private String name;

    public QrCodeRepository() {}

    public QrCode getParsedQrCode() {
        // TODO: Parse location
        return new QrCode(hash, score, null, name);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QrCode getQrCode() {
        return new QrCode(hash, score, null, name);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.CANADA, "%s", name);
    }
}
