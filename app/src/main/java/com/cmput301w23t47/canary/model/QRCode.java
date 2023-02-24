package com.cmput301w23t47.canary.model;

import java.util.ArrayList;

/**
 * Class for the QR Code
 */
public class QRCode {
    // Hash of QR
    // TODO: Verify type
    private String hash;

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

    // score of the QR
    private long score;

    ArrayList<Comment> comments;
}
