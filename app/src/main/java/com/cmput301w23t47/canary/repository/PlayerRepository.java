package com.cmput301w23t47.canary.repository;

import androidx.annotation.NonNull;

import com.cmput301w23t47.canary.model.LeaderboardPlayer;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Models the storage for models on Firestore
 */
public class PlayerRepository {
    // Document Id
    @DocumentId
    private String docId;
    // username of the player
    private String username;
    // firstname of the player
    private String firstName;
    // lastname of the player
    private String lastName;
    // score of player
    private long score;
    // QR with max score
    private long maxScoreQr;
    private ArrayList<PlayerQrCodeRepository> qrCodes;


    // Default Constructor
    public PlayerRepository() {
        qrCodes = new ArrayList<>();
    }

    public Player retrieveParsedPlayer() {
        Player player = new Player(username, firstName, lastName);
        ArrayList<PlayerQrCode> playerQrCodes = new ArrayList<>();
        for (PlayerQrCodeRepository qrRepo : qrCodes) {
            playerQrCodes.add(qrRepo.retrieveParsedPlayerQrCode());
        }
        player.setQrCodes(playerQrCodes);
        return player;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<PlayerQrCodeRepository> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(ArrayList<PlayerQrCodeRepository> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getMaxScoreQr() {
        return maxScoreQr;
    }

    public void setMaxScoreQr(long maxScoreQr) {
        this.maxScoreQr = maxScoreQr;
    }

    public LeaderboardPlayer retrieveLeaderboardPlayer() {
        return new LeaderboardPlayer(username, score, maxScoreQr);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.CANADA, "{%s, %s, %s, %d}", username, firstName, lastName, qrCodes.size());
    }

    public void setFieldsInPlayer(Player player) {
        player.setUsername(username);
        player.setFirstName(firstName);
        player.setLastName(lastName);
    }

    /**
     * Determines if the player repo contains the reference to the given qr
     * @param qrRef the reference to compare
     * @return true if it contains the reference
     */
    public boolean containsQrRef(DocumentReference qrRef) {
        for (PlayerQrCodeRepository qrRepo : qrCodes) {
            if (qrRepo.getQrCode().equals(qrRef)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the qr to the player stats
     * @param playerQrCode the qr added
     */
    public void addQrToPlayerStats(PlayerQrCode playerQrCode) {
        score += playerQrCode.getQrCode().getScore();
        maxScoreQr = Math.max(maxScoreQr, playerQrCode.getQrCode().getScore());
    }
}
