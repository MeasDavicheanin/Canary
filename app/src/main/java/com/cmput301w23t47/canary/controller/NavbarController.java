package com.cmput301w23t47.canary.controller;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;

import com.cmput301w23t47.canary.R;

/**
 * Controller for handling the navigation from the bottom navbar
 */
public class NavbarController {
    // Tag used for debugging
    private static final String TAG = "NavbarController";

    /**
     * Handles the selection on navbar
     * Switches to the
     * @param selectedItem  the selected item
     * @param activity      the current activity
     */
    public static void handleSelection(MenuItem selectedItem, Activity activity) {
        switch (selectedItem.getItemId()) {
            case R.id.page_home:
                Log.d(TAG, "handleSelection: home");
                break;
            case R.id.page_search:
                Log.d(TAG, "handleSelection: search");
                break;
            case R.id.page_rank:
                Log.d(TAG, "handleSelection: rank");
                break;
            case R.id.page_players:
                Log.d(TAG, "handleSelection: player");
                break;
            case R.id.page_profile:
                Log.d(TAG, "handleSelection: profile");
                break;
        }
    }
}
