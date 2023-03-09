package com.cmput301w23t47.canary.view.fragment;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301w23t47.canary.MainActivity;
import com.cmput301w23t47.canary.callback.UpdatePlayerQrCallback;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.databinding.FragmentQrCodeViewBinding;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.Snapshot;

import java.util.Locale;


public class QRCodeViewFragment extends Fragment implements UpdatePlayerQrCallback {
    private PlayerQrCode playerQrCode;

    private FragmentQrCodeViewBinding binding;
    private TextView currentQRCodeName;
    private TextView currentQRCodeScore;
    private TextView currentQRCodeScanDateTime;
    private TextView currentQRCodeScanLocation;
    private ImageView currentQRCodeImage;
    ListView commentsListView;

    private FirestorePlayerController firestorePlayerController = new FirestorePlayerController();

    // Default constructor
    public QRCodeViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateFragmentData(){
        if (playerQrCode == null) {
            return;
        }
        String name = playerQrCode.getName();
        Location location = playerQrCode.getLocation();
        Snapshot qrCodeImage = playerQrCode.getSnapshot();
        long score = playerQrCode.getQrCode().getScore();
        // **Need to set currentQRCodeScanLocation, currentQRCodeImage, currentQRCodeScanDateTime**
        // we don't have any scan date time for the player qr codes yet
        binding.QRCodeName.setText(playerQrCode.getName());
        binding.QRCodeScore.setText(String.format(Locale.CANADA, "Score: %d Pts", playerQrCode.getQrCode().getScore()));
        // TODO: Set location and date
        binding.QRCardLocation.setText("Edmonton");
        binding.QRCodeScanDateTime.setText("Today");
        if (playerQrCode.getSnapshot() != null) {
            binding.QRCodeSnapshot.setImageBitmap(playerQrCode.getSnapshot().getBitmap());
        }
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
//        currentQRCodeName = view.findViewById(R.id.QRCodeName);
//        currentQRCodeScore = view.findViewById(R.id.QRCodeScore);
//        currentQRCodeScanLocation = view.findViewById(R.id.QRCardLocation);
//        currentQRCodeScanDateTime = view.findViewById(R.id.QRCodeScanDateTime);
//        currentQRCodeImage = view.findViewById(R.id.QRCodeImage);
        updateFragmentData();
        return binding.getRoot();
    }

    @Override
    public void updatePlayerQr(PlayerQrCode playerQrCode) {

    }
}