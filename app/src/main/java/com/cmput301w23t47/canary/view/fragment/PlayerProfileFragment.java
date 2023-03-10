package com.cmput301w23t47.canary.view.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.callback.GetPlayerCallback;
import com.cmput301w23t47.canary.controller.FirestoreController;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.databinding.FragmentPlayerProfileBinding;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.view.adapter.QRCodeListAdapter;

import java.util.ArrayList;


public class PlayerProfileFragment extends Fragment implements
        GetPlayerCallback {

    private FragmentPlayerProfileBinding binding;
    private FirestorePlayerController firestorePlayerController = new FirestorePlayerController();
    private ProgressDialog progressDialog;
    private Player player;
    private QRCodeListAdapter qrCodeListAdapter;
    private static final String progressBarTitle = "Loading Player Profile";
    private static final String progressBarMessage = "Should take only a moment...";

    public PlayerProfileFragment() {}

    public static PlayerProfileFragment newInstance() {
        PlayerProfileFragment fragment = new PlayerProfileFragment();
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
        binding = FragmentPlayerProfileBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        progressDialog = new ProgressDialog(getContext());
        firestorePlayerController.getCompleteCurrentPlayer(this);
        qrCodeListAdapter = new QRCodeListAdapter(getContext(), new ArrayList<>());
        binding.qrsScannedList.setAdapter(qrCodeListAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        if(!hidden && player == null){
            firestorePlayerController.getCompleteCurrentPlayer(this);
            initProgressBar();
        }
    }

    private void initProgressBar() {
        progressDialog.setTitle(progressBarTitle);
        progressDialog.setMessage(progressBarMessage);
        progressDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updatePlayerImage(Player player) {
        Bitmap playerImage = player.getPlayerImage();
        if (playerImage != null) binding.playerImage.setImageBitmap(playerImage);
        else {
            String firstLetter = String.valueOf(player.getFirstName().charAt(0)).toUpperCase();
            binding.playerImage.setImageDrawable(null);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, Color.BLACK);
            binding.playerImage.setImageDrawable(drawable);
        }
    }

    private void updateView() {
        updatePlayerImage(this.player);
        binding.playerUsername.setText(player.getUsername());
        binding.playerScore.setText(Long.toString(player.getScore()));
        binding.playerQrsScanned.setText(Integer.toString(player.getQrCodes().size()));
        binding.highestQrScore.setText(Long.toString(player.getHighestQr()));
        binding.lowestQrScore.setText(Long.toString(player.getLowestQr()));
        qrCodeListAdapter.setQrList(player.getQrCodes());
        qrCodeListAdapter.notifyDataSetChanged();
    }


    @Override
    public void getPlayer(Player player) {
        this.player = player;
        updateView();
    }
}