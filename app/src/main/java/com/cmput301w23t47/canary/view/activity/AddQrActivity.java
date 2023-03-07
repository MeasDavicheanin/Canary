package com.cmput301w23t47.canary.view.activity;

import android.os.Bundle;

import com.cmput301w23t47.canary.controller.QrCodeUtil;
import com.cmput301w23t47.canary.databinding.ActivityAddQrBinding;
import com.cmput301w23t47.canary.view.contract.QrCodeContract;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.cmput301w23t47.canary.R;

public class AddQrActivity extends AppCompatActivity {

    public static final String TAG = "AddQrActivity";

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddQrBinding binding;

    private ActivityResultLauncher<Object> qrActivityLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_qr);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_qr);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void init() {
        // register contract for QR Activity and launch scan QR Activity
        qrActivityLauncher = registerForActivityResult(new QrCodeContract(),
                this::receivedQrCode);
        qrActivityLauncher.launch(null);
    }

    /**
     * Receives the QR Code scanned
     * @param qrCodeVal (String): The raw value of the scanned QR Code
     */
    private void receivedQrCode(String qrCodeVal) {
        if (qrCodeVal == null) {
            // value not received
            return;
        }
        // Get Preferences for QR

    }

}