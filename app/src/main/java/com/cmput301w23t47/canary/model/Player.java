package com.cmput301w23t47.canary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

/**
 * Class for Player
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


    public Player(String username, String firstName, String lastName){
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
