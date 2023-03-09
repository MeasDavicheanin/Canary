package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.MainActivity;
import com.cmput301w23t47.canary.callback.UpdatePlayerQrCallback;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.controller.LocationController;
import com.cmput301w23t47.canary.databinding.FragmentQrCodeViewBinding;
import com.cmput301w23t47.canary.model.PlayerQrCode;

import java.util.Locale;


public class QRCodeViewFragment extends Fragment implements UpdatePlayerQrCallback {
    private PlayerQrCode playerQrCode;

    private FragmentQrCodeViewBinding binding;

    private final FirestorePlayerController firestorePlayerController = new FirestorePlayerController();

    // Default constructor
    public QRCodeViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Updates the location if available
     */
    private void updateLocation() {
        // set location
        if (playerQrCode.isLocationShared()) {
            String cityName = LocationController.retrieveCityName(playerQrCode.getLocation());
            if (cityName.equals("")) {
                // no city name given
                binding.qrScanLocation.setText("_ _ _");
            } else {
                binding.qrScanLocation.setText(cityName);
            }
        }
    }

    /**
     * Updates the snapshot if available
     */
    private void updateSnapshot() {
        if (playerQrCode.getSnapshot() != null) {
            binding.qrSnapshot.setImageBitmap(playerQrCode.getSnapshot().getBitmap());
        }
    }

    public void updateFragmentData(){
        if (playerQrCode == null) {
            return;
        }
        // set qr info
        binding.qrTitle.setText(playerQrCode.getName());
        binding.qrScoreVal.setText(String.format(Locale.CANADA, "Score: %d Pts", playerQrCode.getQrCode().getScore()));
        binding.qrScanDate.setText(playerQrCode.getScanDate().toString());
        updateLocation();
        updateSnapshot();
    }

    /**
     * Initializes the view
     */
    private void init() {
        String qrHash = QRCodeViewFragmentArgs.fromBundle(getArguments()).getQrHash();
        firestorePlayerController.getPlayerQr(qrHash, MainActivity.playerUsername, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQrCodeViewBinding.inflate(inflater, container, false);
        init();
        updateFragmentData();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        String qrHash = QRCodeViewFragmentArgs.fromBundle(getArguments()).getQrHash();
        firestorePlayerController.getPlayerQr(qrHash, MainActivity.playerUsername, this);
    }

    @Override
    public void updatePlayerQr(PlayerQrCode playerQrCode) {
        this.playerQrCode = playerQrCode;
        updateFragmentData();
    }
}