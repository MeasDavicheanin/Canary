package com.cmput301w23t47.canary.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;

public class Qrcodem  implements Parcelable {
    private String qrHash;
    private Location location;
    private String name;
    private int score;

    public Qrcodem(String qrHash, Location location, String name, int score){
        this.qrHash = qrHash;
        this.location = location;
        this.name = name;
        this.score = score;
    }

    public Qrcodem(String qrHash, GeoPoint location, String name, int score){
        this.qrHash = qrHash;
        this.location = new Location("");
        this.location.setLatitude(location.getLatitude());
        this.location.setLongitude(location.getLongitude());
        this.name = name;
        this.score = score;
    }

    public Qrcodem() {
    }


    public String getQrHash() {
        return qrHash;
    }

    public void setQrHash(String qrHash) {
        this.qrHash = qrHash;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //added for tthe implementation of parcelable

    protected Qrcodem(Parcel in) {
        qrHash = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        name = in.readString();
        score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(qrHash);
        dest.writeParcelable(location, flags);
        dest.writeString(name);
        dest.writeInt(score);
    }

    public static final Parcelable.Creator<Qrcodem> CREATOR = new Parcelable.Creator<Qrcodem>() {
        @Override
        public Qrcodem createFromParcel(Parcel in) {
            return new Qrcodem(in);
        }

        @Override
        public Qrcodem[] newArray(int size) {
            return new Qrcodem[size];
        }
    };
    
    public String getScoreString(){
        return String.valueOf(score);
    }
}
