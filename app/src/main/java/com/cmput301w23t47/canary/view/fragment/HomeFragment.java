package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.controller.QrCodeUtil;
import com.cmput301w23t47.canary.databinding.FragmentHomeBinding;
import com.cmput301w23t47.canary.view.contract.QrCodeContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";

    private ActivityResultLauncher<Object> qrActivityLauncher;

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    /**
     * Initialization for fragment
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

    /**
     * Receives the QR Code scanned
     * @param qrCodeVal (String): The raw value of the scanned QR Code
     */
    private void receivedQrCode(String qrCodeVal) {
        if (qrCodeVal == null) {
            // value not received
            return;
        }
        byte[] qrHash = QrCodeUtil.getHashForQr(qrCodeVal);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < qrHash.length; i++) {
            str.append(qrHash[i]);
        }
        binding.textView.setText(qrCodeVal + "\n " + str);
    }

    /**
     * Launches the Scan Qr Activity
     */
    private void launchScanQrActivity() {
        qrActivityLauncher.launch(null);
    }
//
//    /**
//     * Call back for updated player
//     * @param player The player object
//     */
//    public void updatePlayer(Player player) {
//        binding.textView.setText(String.format("%s %s %d", player.getFirstName(),
//                player.getLastName(), player.getQrCodes().size()));
//
//    }
}