package com.cmput301w23t47.canary.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * Controller for Snapshot
 * @author Meharpreet Singh Nanda
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

    /**
     * Gets the Bitmap image from the base64 image
     * @param base64Image the image encoded as base64
     * @return the bitmap
     */
    public static Bitmap getImage(String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}
