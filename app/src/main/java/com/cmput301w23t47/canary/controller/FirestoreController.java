package com.cmput301w23t47.canary.controller;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301w23t47.canary.MainActivity;
import com.cmput301w23t47.canary.callback.UpdatePlayerCallback;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.repository.PlayerQRCodeRepository;
import com.cmput301w23t47.canary.repository.PlayerRepository;
import com.cmput301w23t47.canary.repository.QrCodeRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Handles the interaction with the database
 * Objects are singleton
 */
public class FirestoreController {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference players = db.collection("Player");
    private final CollectionReference qrCodes = db.collection("QRCode");
    private final CollectionReference leaderboard = db.collection("Leaderboard");

    private static final String TAG = "Firestore Controller";

    // singleton instance of object
    private static FirestoreController instance;

    /**
     * Gets the singleton instance of object
     * @return the singleton instance of object
     */
    public static FirestoreController getInstance() {
        if (instance == null) {
            instance = new FirestoreController();
        }
        return instance;
    }

    private <TResult> TResult waitForTask(Task<DocumentSnapshot> snapshotTask, @NonNull Class<TResult> classType) {
        try {
            DocumentSnapshot doc = Tasks.await(snapshotTask);
            return doc.toObject(classType);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the Player from db
     * @param playerId the id of the player
     */
    public void getPlayer(String playerId, UpdatePlayerCallback callback) {
        Handler handler = new Handler();
        new Thread(() -> {
            // get Player Repo
            Task<DocumentSnapshot> playerTask = players.document(playerId).get();
            PlayerRepository playerRepository = waitForTask(playerTask, PlayerRepository.class);
            Log.d(TAG, "getPlayer: " + playerRepository.toString());
            // Get associated QR Repos
            for (PlayerQRCodeRepository playerQrCodesRepo : playerRepository.getQrCodes()) {
                QrCodeRepository qrCodeRepo = waitForTask(playerQrCodesRepo.getQrCode().get(), QrCodeRepository.class);
                playerQrCodesRepo.setParsedQrCode(qrCodeRepo.getParsedQrCode());
            }
            Player player = playerRepository.getParsedPlayer();
            handler.post(() -> {
                callback.updatePlayer(player);
            });
        }).start();
    }
}
