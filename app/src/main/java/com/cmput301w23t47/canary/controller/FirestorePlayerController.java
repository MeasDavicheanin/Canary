package com.cmput301w23t47.canary.controller;

import android.os.Handler;

import com.cmput301w23t47.canary.callback.DoesResourceExistCallback;
import com.cmput301w23t47.canary.callback.OperationStatusCallback;
import com.cmput301w23t47.canary.callback.GetPlayerQrCallback;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.repository.GetIndexArg;
import com.cmput301w23t47.canary.repository.PlayerQrCodeRepository;
import com.cmput301w23t47.canary.repository.PlayerRepository;
import com.cmput301w23t47.canary.repository.QrCodeRepository;
import com.cmput301w23t47.canary.repository.SnapshotRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Firestore controller for interacting with Player model
 */
public class FirestorePlayerController extends FirestoreController{
    FirestoreLeaderboardController firestoreLeaderboardController = new FirestoreLeaderboardController();

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
            if (playerQrCode.getSnapshot() != null) {
                snapshotReference = persistSnapshot(playerQrCode.getSnapshot().getBitmap(), username);
            }
            playerRepo.getQrCodes().add(new PlayerQrCodeRepository(qrReference, snapshotReference,
                    new Timestamp(playerQrCode.getScanDate()), playerQrCode.retrieveScore()));
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
    public void getPlayerQr(String qrHash, String username, GetPlayerQrCallback callback) {
        Handler handler = new Handler();
        new Thread(() -> {
            PlayerRepository playerRepo = getPlayerRepo(username);
            Task<QuerySnapshot> qrCodeQuery = qrCodes.whereEqualTo("hash", qrHash).get();
            waitForQuery(qrCodeQuery);
            if (qrCodeQuery.getResult().isEmpty()) {
                handler.post(() -> {
                    callback.getPlayerQr(null);
                });
                // the given qr does not exist
                return;
            }
            QrCodeRepository qrRepo = qrCodeQuery.getResult().getDocuments().get(0).toObject(QrCodeRepository.class);
            DocumentReference qrRef = qrCodeQuery.getResult().getDocuments().get(0).getReference();
            GetIndexArg indexArg = new GetIndexArg();
            SnapshotRepository snapRepo = retrieveQrSnapshotFromPlayer(playerRepo, qrRef, indexArg);
            Integer a = new Integer(1);
            handler.post(() -> {
                // return the playerQr Model
                Timestamp scanDate = playerRepo.getQrCodes().get(indexArg.i).getScanDate();
                callback.getPlayerQr(PlayerQrCodeRepository.retrievePlayerQrCode(qrRepo, snapRepo, scanDate));
            });
        }).start();
    }

    /**
     * Retrieves the snapshot repo if it exists
     * @param playerRepo the player repo that contains the qr ref and snapshot ref
     * @param qrRef the qr ref to search
     * @param indexArg gets the index of the found element
     * @return the snapshot repo if it exists
     */
    public SnapshotRepository retrieveQrSnapshotFromPlayer(PlayerRepository playerRepo, DocumentReference qrRef, GetIndexArg indexArg) {
        DocumentReference snapRef = null;
        for (int i = 0; i < playerRepo.getQrCodes().size(); i++) {
            PlayerQrCodeRepository playerQrCodeRepository = playerRepo.getQrCodes().get(i);
            if (qrRef.equals(playerQrCodeRepository.getQrCode())) {
                snapRef = playerQrCodeRepository.getSnapshot();
                indexArg.i = i;
                break;
            }
        }
        if (snapRef == null) {
            return null;
        }
        Task<DocumentSnapshot> snapTask = snapRef.get();
        return waitForTask(snapTask, SnapshotRepository.class);
    }

    /**
     * Deletes the Qr from the player
     * @param playerQrCode the qr to delete
     * @param callback the callback to inform
     */
    public void deleteQrFromPlayer(PlayerQrCode playerQrCode, OperationStatusCallback callback) {
        Handler handler = new Handler();
        new Thread(() -> {
            // get the player and qr
            String playerDocId = identifyPlayer();
            PlayerRepository playerRepo = getPlayerRepo(playerDocId);
            Task<QuerySnapshot> qrCodeQuery = qrCodes.whereEqualTo("hash", playerQrCode.getQrCode()).get();
            waitForQuery(qrCodeQuery);
            if (qrCodeQuery.getResult().isEmpty()) {
                // the given qr does not exist
                handler.post(() -> {
                    callback.operationStatus(false);
                });
                return;
            }

            // delete the qr, snapshot from player
            DocumentReference qrRef = qrCodeQuery.getResult().getDocuments().get(0).getReference();
            int qrIndex = getIndexOfQrInPlayer(playerRepo, qrRef);
            if (qrIndex < 0) {
                handler.post(() -> {
                    callback.operationStatus(false);
                });
                return;
            }

            deleteSnapshotForQr(playerRepo, qrIndex);
            playerRepo.removeQrAt(qrIndex, playerQrCode);
            Task<Void> playerUpdateTask = players.document(playerDocId).set(playerRepo);
            waitForUpdateTask(playerUpdateTask);
            firestoreLeaderboardController.updateLeaderboardIfRequired(playerRepo);
        }).start();
    }

    /**
     * Gets the index of the qr in the player
     * @param playerRepo the player to search
     * @param qrRef the reference to the qr
     * @return the index of the qr in player
     */
    protected int getIndexOfQrInPlayer(PlayerRepository playerRepo, DocumentReference qrRef) {
        for (int i = 0; i < playerRepo.getQrCodes().size(); i++) {
            PlayerQrCodeRepository playerQrCodeRepository = playerRepo.getQrCodes().get(i);
            if (qrRef.equals(playerQrCodeRepository.getQrCode())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Deletes the snapshot for the qr from the player
     * @param playerRepo the player repo
     * @param qrIndex the index of the qr in the player repo
     */
    protected void deleteSnapshotForQr(PlayerRepository playerRepo, int qrIndex) {
        DocumentReference snapRef = playerRepo.getQrCodes().get(qrIndex).getSnapshot();
        if (snapRef == null) {
            return;
        }
        Task<Void> deleteTask = snapRef.delete();
        waitForUpdateTask(deleteTask);
    }
}
