package com.cmput301w23t47.canary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Class for Player
 */
public class Player implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeLong(score);
        parcel.writeList(qrCodes);
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    private Player(Parcel in) {
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        score = in.readLong();
        // Complete function
    }
}
