package com.cmput301w23t47.canary.model;

import android.location.Location;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

/**
 * Class for WorldQRLIST
 * this should be a global list of all the QR codes in the world
 */
public class WorldQRLIST {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference qrRef = db.collection("QRcode");
    public ArrayList<QRcode> qrList;
    private QRcode firebaseQRcode;

    public WorldQRLIST() {
        qrList = new ArrayList<QRcode>();
    }

    //Check make sure correct QR code is added
    public void initCloud() {
        qrRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QRcode qrCode : task.getResult().toObjects(QRcode.class)) {
                    qrList.add(qrCode);
                }
            }
        });
    }
    public void addQRcode(String qrHash, GeoPoint location, String name, int score) {
        QRcode qrCode = new QRcode(qrHash, location, name,score);
        qrList.add(qrCode);
    }
    public void addQRcode(QRcode qrCode) {
        qrList.add(qrCode);
    }

    public void removeQRcode(QRcode qrCode) {
        qrList.remove(qrCode);
    }

    public ArrayList<QRcode> getQrList() {
        return qrList;
    }

    public void setQrList(ArrayList<QRcode> qrList) {
        this.qrList = qrList;
    }

    public void getSize() {
        qrList.size();
    }
}
