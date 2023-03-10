package com.cmput301w23t47.canary.view.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t47.canary.view.activity.AddQrActivity;

/**
 * Contract for adding a new QR
 * Returns the string which stores the hash of the scanned QR
 */
public class AddNewQrContract extends ActivityResultContract<Object, String> {
    static public final String RESPONSE_TAG = "AddNewQrContractResp";

    /**
     * Creates the intent for launching the application
     */
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Object o) {
        return new Intent(context, AddQrActivity.class);
    }

    /**
     * Parses the result and returns it
     * @return the result string
     */
    @Override
    public String parseResult(int i, @Nullable Intent intent) {
        if (i == Activity.RESULT_OK) {
            return intent.getStringExtra(RESPONSE_TAG);
        }
        return null;
    }
}
