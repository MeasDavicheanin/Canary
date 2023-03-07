package com.cmput301w23t47.canary.controller;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Util controller for interacting with the QR Code
 */
public class QrCodeUtil {
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
        byte[] qrHash = QrCodeUtil.getHashForQr(qrVal);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < qrHash.length; i++) {
            str.append(qrHash[i]);
        }
        return str.toString();
    }
}
