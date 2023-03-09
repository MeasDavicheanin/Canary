package com.cmput301w23t47.canary.repository;

import com.cmput301w23t47.canary.controller.SnapshotController;
import com.cmput301w23t47.canary.model.Snapshot;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

/**
 * Repository for the snapshot
 */
public class SnapshotRepository {
    @DocumentId
    private String docId;

    private String data;
    private DocumentReference owner;

    // Default Constructor
    public SnapshotRepository() {}

    // Param constructor
    public SnapshotRepository(String data, DocumentReference owner) {
        this.data = data;
        this.owner = owner;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DocumentReference getOwner() {
        return owner;
    }

    public void setOwner(DocumentReference owner) {
        this.owner = owner;
    }

    /**
     * Retrieves the Snapshot object from the repo model
     * @return the Snapshot object
     */
    public Snapshot retrieveSnapshot() {
        return new Snapshot(SnapshotController.getImage(data));
    }
}
