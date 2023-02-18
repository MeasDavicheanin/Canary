/*
 * Copyright 2022 The Android Open Source Project, Meharpreet Singh Nanda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.cmput301w23t47.canary.view.model;

import android.view.MotionEvent;
import android.view.View;

import com.google.mlkit.vision.barcode.common.Barcode;

/**
 * Represents the scanned QR for the view
 * Reference: https://github.com/android/camera-samples/tree/main/CameraX-MLKit
 * @author Meharpreet Singh Nanda
 */
public class QrCodeVm {
    private Barcode qrCode;
    private String content;

    /**
     * Callback for pressing on the QR Code box
     */
    public boolean qrCodePressCallback (View view, MotionEvent event) {
        // Add callback code
        return false;
    }

    /**
     * Getter for content of qrCode
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for content of qrCode
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Constructor for the VM
     */
    public QrCodeVm(Barcode qrCode) {
        this.qrCode = qrCode;
        content = qrCode.getRawValue();
    }

    /**
     * Getter for QRCode
     */
    public Barcode getQrCode() {
        return qrCode;
    }

    /**
     * Setter for qrCode
     */
    public void setQrCode(Barcode qrCode) {
        this.qrCode = qrCode;
    }
}
