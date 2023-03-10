package com.cmput301w23t47.canary.controller;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;

import com.cmput301w23t47.canary.R;

/**
 * Controller for handling the navigation from the bottom navbar
 */
public class NavbarController {
    public interface NavigateToPage {
        void navigateToHome();
        void navigateToSearch();
        void navigateToLeaderboard();
        void navigateToPlayers();
        void navigateToProfile();
    }

    // Tag used for debugging
    private static final String TAG = "NavbarController";

    /**
     * Handles the selection on navbar
     * Switches to the
     * @param selectedItem  the selected item
     * @param activity      the current activity
     */
    public static void handleSelection(MenuItem selectedItem, NavigateToPage navigator) {
//        switch (selectedItem.getItemId()) {
//            case R.id.page_home:
//                navigator.navigateToHome();
//                break;
//            case R.id.page_search:
//                Log.d(TAG, "handleSelection: search");
//                break;
//            case R.id.page_rank:
//                navigator.navigateToLeaderboard();
//                break;
//            case R.id.page_players:
//                Log.d(TAG, "handleSelection: player");
//                break;
//            case R.id.page_profile:
//                Log.d(TAG, "handleSelection: profile");
//                break;
//        }
    }
}
