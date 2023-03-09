package com.cmput301w23t47.canary.repository;

import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.QrCode;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

/**
 * Models the storage for QR Scanned by QR
 */
public class PlayerQrCodeRepository {
    // TODO: Add support for snapshot
    // Store the reference to the QRCode object
    private DocumentReference qrCode;
    // store the reference to the snapshot
    private DocumentReference snapshot;
    // store the date the qr was scanned
    private Timestamp scanDate;

    @Exclude
    // stores the parsed playerQrCode
    private PlayerQrCode parsedPlayerQrCode;

    public PlayerQrCodeRepository() {
        parsedPlayerQrCode = new PlayerQrCode();
    }

    public PlayerQrCodeRepository(DocumentReference qrCode, DocumentReference snapshot, Timestamp scanDate) {
        this.qrCode = qrCode;
        this.snapshot = snapshot;
        this.scanDate = scanDate;
    }

    public void setParsedQrCode(QrCode qrCode) {
        this.parsedPlayerQrCode.setQrCode(qrCode);
    }

    @Exclude
    public PlayerQrCode retrieveParsedPlayerQrCode() {
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

    public Timestamp getScanDate() {
        return scanDate;
    }

    public void setScanDate(Timestamp scanDate) {
        this.scanDate = scanDate;
    }

    /**
     * Retrieves the player qr code
     * @param qrCodeRepository the QrCodeRepo
     * @param snapRepo the snapshot repo
     * @return the PlayerQrCode object
     */
    public static PlayerQrCode retrievePlayerQrCode(QrCodeRepository qrCodeRepository, SnapshotRepository snapRepo, Timestamp date) {
        PlayerQrCode playerQrCode = new PlayerQrCode(qrCodeRepository.retrieveParsedQrCode(), date.toDate());
        playerQrCode.setSnapshot(snapRepo.retrieveSnapshot());
        return playerQrCode;
    }
}
