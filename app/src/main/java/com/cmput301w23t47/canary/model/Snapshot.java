package com.cmput301w23t47.canary.model;

import android.graphics.Bitmap;

/**
 * Class for Snapshot
 */
public class Snapshot {
    private Bitmap bitmap;
    public Snapshot(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
