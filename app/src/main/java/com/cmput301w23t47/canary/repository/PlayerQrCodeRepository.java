package com.cmput301w23t47.canary.repository;

import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.QrCode;
import com.google.firebase.firestore.DocumentReference;

/**
 * Models the storage for QR Scanned by QR
 */
public class PlayerQrCodeRepository {
    // TODO: Add support for snapshot
    // Store the reference to the QRCode object
    private DocumentReference qrCode;
    // store the reference to the snapshot
    private DocumentReference snapshot;
    // stores the parsed playerQrCode
    private PlayerQrCode parsedPlayerQrCode;

    public PlayerQrCodeRepository() {
        parsedPlayerQrCode = new PlayerQrCode();
    }

    public void setParsedQrCode(QrCode qrCode) {
        this.parsedPlayerQrCode.setQrCode(qrCode);
    }

    public PlayerQrCode getParsedPlayerQrCode() {
        return parsedPlayerQrCode;
    }

    public DocumentReference getQrCode() {
        return qrCode;
    }

    public void setQrCode(DocumentReference qrCode) {
        this.qrCode = qrCode;
    }

    public DocumentReference getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(DocumentReference snapshot) {
        this.snapshot = snapshot;
    }
}
