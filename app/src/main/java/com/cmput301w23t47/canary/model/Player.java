package com.cmput301w23t47.canary.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Class for Player
 */
public class Player {
    // username of the player
    private String username;
    // firstname of the player
    private String firstName;
    // lastname of the player
    private String lastName;
    // list of QR codes scanned by user
    private ArrayList<PlayerQrCode> qrCodes;
    // score of the player
    private long score = 0;
    // unique ID of the player, stored as the document name on firestore
    private String uniquePlayerId;
    private Bitmap playerImage;


    public Player(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.qrCodes = new ArrayList<>();
    }

    public void addQrCode(PlayerQrCode playerQrCode) {
        qrCodes.add(playerQrCode);
    }

    public Player() {}

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

    public ArrayList<PlayerQrCode> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(ArrayList<PlayerQrCode> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getUniquePlayerId() {
        return uniquePlayerId;
    }

    public void setUniquePlayerId(String uniquePlayerId) {
        this.uniquePlayerId = uniquePlayerId;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Bitmap playerImage) {
        this.playerImage = playerImage;
    }

    public long getHighestQr(){
        long highest = 0;
        for (PlayerQrCode qr: qrCodes){
            long qrScore = qr.getQrCode().getScore();
            if( qrScore > highest)
                highest = qrScore;
        }
        return highest;
    }
    public long getLowestQr(){
        long lowest = Long.MAX_VALUE;
        for (PlayerQrCode qr: qrCodes){
            long qrScore = qr.getQrCode().getScore();
            if( qrScore < lowest)
                lowest = qrScore;
        }
        return lowest;
    }

}
