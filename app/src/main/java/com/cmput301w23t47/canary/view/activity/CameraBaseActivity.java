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

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.mlkit.vision.MlKitAnalyzer;
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.cmput301w23t47.canary.databinding.ActivityScanQrcodeBinding;
import com.cmput301w23t47.canary.view.drawable.QrCodeDrawable;
import com.cmput301w23t47.canary.view.model.QrCodeVm;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Base class for the Activity that interacts with the camera
 * Reference: https://github.com/android/camera-samples/tree/main/CameraX-MLKit
 *
 * @author Meharpreet Singh Nanda
 */
abstract public class CameraBaseActivity extends AppCompatActivity {
    protected static final String[] CAM_PERMISSIONS = {Manifest.permission.CAMERA};

    protected ActivityScanQrcodeBinding binding;
    protected BarcodeScanner qrScanner;

    /**
     * Checks if the app has permission to access the camera.
     * Requests for permissions if not
     */
    protected void checkPermissions() {
        if (arePermissionsGranted()) {
            openCamera();
        } else {

        }
    }

    /**
     * Determines if all the required permissions have been granted
     * @return boolean: Whether the permissions have been granted
     */
    protected boolean arePermissionsGranted() {
        // determine if all the permissions have been granted or not
        for (String perm : CAM_PERMISSIONS) {
            if (!(ContextCompat.checkSelfPermission(getBaseContext(), perm) == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Turns on the camera
     */
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
