package com.cmput301w23t47.canary.controller;

import android.os.Handler;

import com.cmput301w23t47.canary.callback.DoesResourceExistCallback;
import com.cmput301w23t47.canary.repository.PlayerRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Firestore controller for interacting with Player model
 */
public class FirestorePlayerController extends FirestoreController{

    /**
     * Determines whether the player has the given qr
     * @param qrHash the hash of the qr
     * @param username the username of the player
     * @param resExistCallback the callback for returning the response
     */
    public void doesPlayerHaveQr(String qrHash, String username, DoesResourceExistCallback resExistCallback) {
        Handler handler = new Handler();
        new Thread(() -> {
            boolean playerHasQr = doesPlayerHaveQr(qrHash, username);
            // call the callback when the result is available
            handler.post(() -> {
                resExistCallback.doesResourceExists(playerHasQr);
            });
        }).start();
    }

    /**
     * Determines whether the player has the given qr
     * @param qrHash the hash of the qr
     * @param username the username of the player
     * @return true if player has the username
     */
    protected boolean doesPlayerHaveQr(String qrHash, String username) {
        // determine if the qr resource exist with the given hash
        Task<QuerySnapshot> qrCodeQuery = qrCodes.whereEqualTo("hash", qrHash).get();
        waitForQuery(qrCodeQuery);
        if (qrCodeQuery.getResult().size() == 0) {
            // qr resource does not exist; player cannot have it
            return false;
        }
        DocumentReference qrDocRef = qrCodeQuery.getResult().getDocuments().get(0).getReference();

        // get the player model
        PlayerRepository playerRepo = getPlayerRepo(username);
        return playerRepo.containsQrRef(qrDocRef);
    }
}
