package com.cmput301w23t47.canary.controller;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * Controller for Snapshot
 */
public class SnapshotController {
    /**
     * Reference: Renjith Thankachan, https://stackoverflow.com/questions/40885860/how-to-save-bitmap-to-firebase
     * @param image the image to compress and encode
     * @return the encoded image
     */
    public static String getBase64Image(Bitmap image) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        image.recycle();
        return Base64.getEncoder().encodeToString(outStream.toByteArray());
    }
}
