package com.cmput301w23t47.canary.callback;

/**
 * Callback interface for informing the caller about the status of the operation
 */
public interface OperationStatusCallback {
    public void operationStatus(boolean status);
}
