package com.cmput301w23t47.canary.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


/**
 * Class for the QR Code scanned by the Player
 */
public class PlayerQrCode implements Parcelable {
    // QR Code scanned
    private QrCode qrCode;

    // Snapshot of the QR
    private Snapshot snapshot;

    public PlayerQrCode() {}

    public PlayerQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    public QrCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public String getName() {return qrCode.getName();}

    public Location getLocation() {return qrCode.getLocation();}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<PlayerQrCode> CREATOR
            = new Parcelable.Creator<PlayerQrCode>() {
        public PlayerQrCode createFromParcel(Parcel in) {
            return new PlayerQrCode(in);
        }

        public PlayerQrCode[] newArray(int size) {
            return new PlayerQrCode[size];
        }
    };

    private PlayerQrCode(Parcel in) {

    }
}
