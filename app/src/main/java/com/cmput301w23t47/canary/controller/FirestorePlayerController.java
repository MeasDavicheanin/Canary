package com.cmput301w23t47.canary.controller;

import android.os.Handler;

import com.cmput301w23t47.canary.callback.DoesResourceExistCallback;
import com.cmput301w23t47.canary.callback.OperationStatusCallback;
import com.cmput301w23t47.canary.callback.UpdatePlayerQrCallback;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.repository.PlayerQrCodeRepository;
import com.cmput301w23t47.canary.repository.PlayerRepository;
import com.cmput301w23t47.canary.repository.QrCodeRepository;
import com.cmput301w23t47.canary.repository.SnapshotRepository;
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

    /**
     * Adds the given qr to the player's model
     * @param playerQrCode the qr to add
     * @param username the username of the player
     * @param callback the callback to inform when operation completes
     */
    public void addQrToPlayer(PlayerQrCode playerQrCode, String username, OperationStatusCallback callback) {
        Handler handler = new Handler();
        new Thread(() -> {
            PlayerRepository playerRepo = getPlayerRepo(username);
            playerRepo.addQrToPlayerStats(playerQrCode);
            QrCodeRepository qrCodeRepository = QrCodeRepository.retrieveQrCodeRepo(playerQrCode.getQrCode());
            DocumentReference qrReference = getReferenceToQrOrCreate(qrCodeRepository);
            DocumentReference snapshotReference = null;
            if (playerQrCode.getSnapshot().getBitmap() != null) {
                snapshotReference = persistSnapshot(playerQrCode.getSnapshot().getBitmap(), username);
            }
            playerRepo.getQrCodes().add(new PlayerQrCodeRepository(qrReference, snapshotReference));
            updatePlayer(playerRepo);
            handler.post(() -> {
               callback.operationStatus(true);
            });
        }).start();
    }

    protected void updatePlayer(PlayerRepository playerRepo) {
        Task<Void> updatePlayerTask = players.document(playerRepo.getDocId()).set(playerRepo);
        waitForUpdateTask(updatePlayerTask);
    }

    /**
     * Get the PlayerQr object
     * @param qrHash the hash of the qr
     * @param username the username of the player
     * @param callback the callback to return the result
     */
    public void getPlayerQr(String qrHash, String username, UpdatePlayerQrCallback callback) {
        Handler handler = new Handler();
        new Thread(() -> {
            PlayerRepository playerRepo = getPlayerRepo(username);
            Task<QuerySnapshot> qrCodeQuery = qrCodes.whereEqualTo("hash", qrHash).get();
            waitForQuery(qrCodeQuery);
            if (qrCodeQuery.getResult().isEmpty()) {
                handler.post(() -> {
                    callback.updatePlayerQr(null);
                });
                // the given qr does not exist
                return;
            }
            QrCodeRepository qrRepo = qrCodeQuery.getResult().getDocuments().get(0).toObject(QrCodeRepository.class);
            DocumentReference qrRef = qrCodeQuery.getResult().getDocuments().get(0).getReference();
            SnapshotRepository snapRepo = retrieveQrSnapshotFromPlayer(playerRepo, qrRef);
            handler.post(() -> {
                // return the playerQr Model
                callback.updatePlayerQr(PlayerQrCodeRepository.retrievePlayerQrCode(qrRepo, snapRepo));
            });
        }).start();
    }

    /**
     * Retrieves the snapshot repo if it exists
     * @param playerRepo the player repo that contains the qr ref and snapshot ref
     * @param qrRef the qr ref to search
     * @return the snapshot repo if it exists
     */
    public SnapshotRepository retrieveQrSnapshotFromPlayer(PlayerRepository playerRepo, DocumentReference qrRef) {
        DocumentReference snapRef = null;
        for (PlayerQrCodeRepository playerQrCodeRepository : playerRepo.getQrCodes()) {
            if (qrRef.equals(playerQrCodeRepository.getQrCode())) {
                snapRef = playerQrCodeRepository.getSnapshot();
                break;
            }
        }
        if (snapRef == null) {
            return null;
        }
        Task<DocumentSnapshot> snapTask = snapRef.get();
//        players.whereEqualTo("qrCodes.qrCode", qrDocRef);
        return waitForTask(snapTask, SnapshotRepository.class);
    }
}
