package com.cmput301w23t47.canary.controller;

import android.os.Handler;

import com.cmput301w23t47.canary.model.Leaderboard;
import com.cmput301w23t47.canary.repository.PlayerRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Controller for operating on the leaderboard collection
 */
public class FirestoreLeaderboardController extends FirestoreController{
    /**
     * Checks if the leaderboard needs to be updated using the new player model
     * @param playerRepo the player that changed
     */
    public void checkIfLeaderboardUpdates(PlayerRepository playerRepo) {
        Handler handler = new Handler();
        new Thread(() -> {
            Task<DocumentSnapshot> lbTask = leaderboard.document(globalLeaderboardDocument).get();
            Leaderboard leaderboard = waitForTask(lbTask, Leaderboard.class);
//            if (leaderboard.get)
        }).start();
    }
}
