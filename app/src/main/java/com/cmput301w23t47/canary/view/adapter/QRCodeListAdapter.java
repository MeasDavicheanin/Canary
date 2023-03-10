package com.cmput301w23t47.canary.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.LeaderboardPlayer;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.QrCode;

import java.util.ArrayList;

public class QRCodeListAdapter extends ArrayAdapter<PlayerQrCode> {

    private ArrayList<PlayerQrCode> playerQrCodesList;

    public QRCodeListAdapter(Context context, ArrayList<PlayerQrCode> qrCodes){
        super(context, 0, qrCodes);
        this.playerQrCodesList = qrCodes;
    }

    public ArrayList<PlayerQrCode> getPlayerQrCodesList() {return playerQrCodesList;}

    /**
     * Sets the qr list in the adapter
     * @param playerQrCodes the new qr list to set
     */
    public void setQrList(ArrayList<PlayerQrCode> playerQrCodes) {
        playerQrCodes.clear();
        playerQrCodes.addAll(playerQrCodes);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(super.getContext()).inflate(R.layout.player_qr_code,parent,false);
        }else{
            view = convertView;
        }
        PlayerQrCode qr = getItem(position);
        ImageView qrCodeImage = view.findViewById(R.id.listQrCodeImage);
        // qrCodeImage.setImageBitmap(qr.getSnapshot()); TODO: fix this later
        TextView qrUsername = view.findViewById(R.id.listQrUsername);
        qrUsername.setText(qr.getName());
        TextView qrCodeScore = view.findViewById(R.id.listQrCodeScore);
        qrCodeScore.setText(Long.toString(qr.getQrCode().getScore()));
        TextView qrCodeScanDateTime = view.findViewById(R.id.listQrCodeScanDateTime);
//        qrCodeScanDateTime.setText(); TODO: fix this later
        return view;
    }
}
