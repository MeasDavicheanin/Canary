package com.cmput301w23t47.canary.model;

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
    public ArrayList<Qrcodem> qrList;
    private Qrcodem firebaseQRcode;

    public WorldQRLIST() {
        this.qrList = new ArrayList<Qrcodem>();
         initCloud();
    }

    //Check make sure correct QR code is added
    public void initCloud() {
        qrRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (Qrcodem qrCode : task.getResult().toObjects(Qrcodem.class)) {
                    qrList.add(qrCode);
                }
            }
        });
    }
    public void addQRcode(String qrHash, GeoPoint location, String name, int score) {
        Qrcodem qrCode = new Qrcodem(qrHash, location, name,score);
        qrList.add(qrCode);
    }
    public void addQRcode(Qrcodem qrCode) {
        qrList.add(qrCode);
    }

    public void removeQRcode(Qrcodem qrCode) {
        qrList.remove(qrCode);
    }

    public ArrayList<Qrcodem> getQrList() {
        return qrList;
    }

    public void setQrList(ArrayList<Qrcodem> qrList) {
        this.qrList = qrList;
    }

    public int getSize() {
        return qrList.size();
    }
    public Qrcodem getQrcode(int index) {
        return qrList.get(index);
    }
    public String getQrName(int index) {
        return qrList.get(index).getName();
    }
    public int getQrPointint(int index) {
        return qrList.get(index).getScore();
    }

    public String getQrPointStr(int index) {
        return Integer.toString(qrList.get(index).getScore());
    }
    
    public void RECALCULATE() {
        qrList.clear();
        initCloud();
        
    }



}
