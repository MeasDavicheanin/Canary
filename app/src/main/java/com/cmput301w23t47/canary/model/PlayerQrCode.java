package com.cmput301w23t47.canary.model;

/**
 * Class for the QR Code scanned by the Player
 */
public class PlayerQrCode {
    // Player who scanned the QR
    private Player player;
    // QR Code scanned
    private QrCode qrCode;

    // Snapshot of the QR
    private Snapshot snapshot;

    public PlayerQrCode() {}

    public PlayerQrCode(Player player, QrCode qrCode) {
        this.player = player;
        this.qrCode = qrCode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
}
