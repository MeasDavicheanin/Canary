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


package com.cmput301w23t47.canary.view.activity;

import static androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.mlkit.vision.MlKitAnalyzer;
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.view.model.QrCodeVm;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.Arrays;
import java.util.List;

/**
 * Activity for Scanning QR Code
 * Reference: https://github.com/android/camera-samples/tree/main/CameraX-MLKit
 * @author Meharpreet Singh Nanda
 */
public class ScanQRCodeActivity extends CameraBaseActivity {

    /**
     * Turns on the camera
     */
    @Override
    protected void openCamera() {
        LifecycleCameraController camController = new LifecycleCameraController(getBaseContext());
        PreviewView preview = binding.cameraView;
        // Add the option to detect all types of QR Codes
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();
        qrScanner = BarcodeScanning.getClient(options);
        camController.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(this),
                getQrCodeAnalyzer(preview));
    }

    /**
     * Gets the analyzer for the images
     * @return MlKitAnalyzer the analyzer object
     */
    protected MlKitAnalyzer getQrCodeAnalyzer(PreviewView preview) {
        return new MlKitAnalyzer(
                Arrays.asList(qrScanner), COORDINATE_SYSTEM_VIEW_REFERENCED, ContextCompat.getMainExecutor(this),
                result -> {
                    List<Barcode> qrResList = result.getValue(qrScanner);
                    if (qrResList == null || qrResList.size() == 0 || qrResList.get(0) == null) {
                        // if there is no result
//                                preview.getOverlay().clear();
//                                // TODO: Understand
//                                preview.setOnTouchListener((view, motionEvent) -> {
//                                    return false;
//                                });
                        return;
                    }

                    QrCodeVm qrVm = new QrCodeVm(qrResList.get(0));
//                            QrCodeDrawable qrCodeDrawable = new QrCodeDrawable();
                    preview.setOnTouchListener(qrVm::qrCodePressCallback);
                    preview.getOverlay().clear();
//                            preview.getOverlay().add(qrCodeDrawable);
                }
        );
    }
}