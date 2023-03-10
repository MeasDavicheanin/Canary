package com.cmput301w23t47.canary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.auth.User;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Class for Player
 * @author Meharpreet Singh Nanda
 */
public class Player  implements Parcelable {
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

    /**
     * Gets the highest qr score
     * @return the highest qr score
     */
    public long getHighestQr(){
        if (qrCodes.isEmpty()) {
            return 0;
        }
        long highest = 0;
        for (PlayerQrCode qr: qrCodes){
            long qrScore = qr.getQrCode().getScore();
            if( qrScore > highest)
                highest = qrScore;
        }
        return highest;
    }

    /**
     * Gets the lowest qr
     * @return the lowest qr
     */
    public long getLowestQr(){
        if (qrCodes.isEmpty()) {
            return 0;
        }
        long lowest = Long.MAX_VALUE;
        for (PlayerQrCode qr: qrCodes){
            long qrScore = qr.getQrCode().getScore();
            if( qrScore < lowest)
                lowest = qrScore;
        }
        return lowest;
    }

    /**
     * Gets the string to draw for the player
     * @return the first char of the username
     */
    public String retrieveStringToDraw() {
        return String.valueOf(username.charAt(0));
    }
    
    //Parcelable methods
    /**
     * Constructor for Player Parcelable
     * @param in
     */
    protected Player(Parcel in) {
         username = in.readString();
         firstName = in.readString();
         lastName = in.readString();
         score = in.readLong();
    }
    
    /**
     * Creator for Player Parcelable
     */
    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }
        
        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    
    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }
    
    /**
     * Flatten this object in to a Parcel.
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeArray( qrCodes.toArray() );
        dest.writeLong(score);
    }
}
