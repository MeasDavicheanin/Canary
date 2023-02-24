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

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.Snapshot;


public class QRCodeView extends Fragment {

    private PlayerQrCode currentPlayerQRCode;
    private TextView currentQRCodeName;
    private TextView currentQRCodeScore;
    private TextView currentQRCodeScanDateTime;
    private TextView currentQRCodeScanLocation;
    private ImageView currentQRCodeImage;
    ListView commentsListView;

    public QRCodeView(PlayerQrCode currentPlayerQRCode) {
        this.currentPlayerQRCode = currentPlayerQRCode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateFragmentData(){
        String name = currentPlayerQRCode.getName();
        Location location = currentPlayerQRCode.getLocation();
        Snapshot qrCodeImage = currentPlayerQRCode.getSnapshot();
        long score = currentPlayerQRCode.getQrCode().getScore();
        // **Need to set currentQRCodeScanLocation, currentQRCodeImage, currentQRCodeScanDateTime**
        // we don't have any scan date time for the player qr codes yet
        if(currentPlayerQRCode != null){
            if(name != null)
                currentQRCodeName.setText(name);
            if(score >= 0)
                currentQRCodeScore.setText("Score "+ String.valueOf(score)+ " Pts");
            if(qrCodeImage != null){
                // fill this once snapshot class is complete
            }
            if(location != null){
                //fill this once location class is complete
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code_view, container, false);
        currentPlayerQRCode = getArguments().getParcelable("playerQRCode");
        currentQRCodeName = view.findViewById(R.id.QRCodeName);
        currentQRCodeScore = view.findViewById(R.id.QRCodeScore);
        currentQRCodeScanLocation = view.findViewById(R.id.QRCardLocation);
        currentQRCodeScanDateTime = view.findViewById(R.id.QRCodeScanDateTime);
        currentQRCodeImage = view.findViewById(R.id.QRCodeImage);
        updateFragmentData();
        return view;
    }
}