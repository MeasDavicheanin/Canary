package com.cmput301w23t47.canary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cmput301w23t47.canary.databinding.ActivityMainBinding;
import com.cmput301w23t47.canary.view.contract.QrCodeContract;

/**
 * Main Acitvity
 * @author Meharpreet Singh Nanda
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    int i = 0;

    private ActivityResultLauncher<Object> qrActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    /**
     * Initialization for activity
     */
    private void init() {
        // add listener for scanQr button
        binding.scanQr.setOnClickListener(view -> {
            launchScanQrActivity();
        });

        // register contract for QR Activity
        qrActivityLauncher = registerForActivityResult(new QrCodeContract(),
                this::receivedQrCode);
    }

    private void receivedQrCode(String qrCodeVal) {
        binding.textView.setText(qrCodeVal);
    }

    /**
     * Launches the Scan Qr Activity
     */
    private void launchScanQrActivity() {
        qrActivityLauncher.launch(null);
    }
}