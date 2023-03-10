package com.cmput301w23t47.canary.controller;

import com.cmput301w23t47.canary.model.QrCode;

/**
 * Calculates the score for the QR
 */
public class ScoreCalculator {
    /**
     * Calculates the score for the given QR
     * @param qrHash (String): the hash of the qr
     * @return the calculated score
     */
    public static long calculateScore(String qrHash) {
        // TODO: Get a valid calculation scheme for score
        long score = 0;
        for (int i = 0; i < qrHash.length(); i++) {
            score += qrHash.charAt(i);
        }
        return score % 47 + 53;
    }
}
