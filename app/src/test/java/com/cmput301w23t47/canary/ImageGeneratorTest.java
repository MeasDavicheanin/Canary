package com.cmput301w23t47.canary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cmput301w23t47.canary.controller.ImageGenerator;

import org.junit.Test;

public class ImageGeneratorTest {
    @Test
    public void image_isGenerated() {
        ImageGenerator image = new ImageGenerator();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        String url =  "https://picsum.photos/200";
        Bitmap bitmap = BitmapFactory.decodeFile(url, options);
        if (options.outWidth != -1 && options.outHeight != -1) {
            // This is an image file.
        }
        else {
            // This is not an image file.
        }
    }

    private final String[] okFileExtensions = new String[] {
            "jpg",
            "png",
            "gif",
            "jpeg"
    };


    public boolean accept(ImageGenerator image) {
        for (String extension: okFileExtensions) {
            //if (image.getName().toLowerCase().endsWith(extension))
            {
                return true;
            }
        }
        return false;
    }
}