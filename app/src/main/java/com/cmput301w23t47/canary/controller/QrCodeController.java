package com.cmput301w23t47.canary.controller;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Util controller for interacting with the QR Code
 * @author Meharpreet Singh Nanda
 */
public class QrCodeController {
    private static final String dateFormat = "dd MMM yyyy";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CANADA);

    /**
     * Gets the Hash for the QR
     * Uses the SHA-256 algorithm as the hashing algorithm
     * @param qrVal (String): the original raw value of the QR Code
     * @return byte[] sha256 hash
     */
    public static byte[] getHashForQr(String qrVal)  {
        try {
            return MessageDigest.getInstance("SHA-256").digest(qrVal.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException err) {
            Log.e("HASH ERR", "getHashForQr: Couldn't find hashing algorithm" );
            err.printStackTrace();
            return new byte[0];
        }
    }

    public static String getHashStrForQr(String qrVal) {
        byte[] qrHash = QrCodeController.getHashForQr(qrVal);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < qrHash.length; i++) {
            str.append(qrHash[i]);
        }
        return str.toString();
    }

    /**
     * Gets the formatted Date time
     * @param date the date to format
     * @return the formatted str for date
     */
    public static String getFormattedDate(Date date) {
        return simpleDateFormat.format(date);
    }
}
