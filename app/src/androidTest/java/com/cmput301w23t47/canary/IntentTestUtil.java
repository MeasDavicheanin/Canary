package com.cmput301w23t47.canary;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;


import android.app.Instrumentation.ActivityResult;

import com.cmput301w23t47.canary.view.activity.ScanQRCodeActivity;
import com.cmput301w23t47.canary.view.contract.QrCodeContract;

/**
 * Util Class for Intent testing
 */
public class IntentTestUtil {
    // The result of the QR Code scanned
    public static final String scanQrCodeResult = "Qr Code Found: Canary T47";

    /**
     * Gets the mock result for the ScanQRCodeActivity
     * @return the intent that mimics the response of activity
     */
    public static ActivityResult getMockResultForScanQrCodeActivity() {
        Intent result = new Intent();
        result.putExtra(QrCodeContract.RESPONSE_TAG, scanQrCodeResult);
        return new ActivityResult(Activity.RESULT_OK, result);
    }
}
