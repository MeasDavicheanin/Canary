package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.callback.GetPlayerCallback;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.controller.SnapshotController;
import com.cmput301w23t47.canary.databinding.FragmentHomeBinding;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.view.contract.AddNewQrContract;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements
        GetPlayerCallback {
    public static final String TAG = "HomeFragment";

    private ActivityResultLauncher<Object> qrActivityLauncher;
    private FragmentHomeBinding binding;
    private final FirestorePlayerController firestorePlayerController = new FirestorePlayerController();

    private Player player;


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
        showLoadingBar();
        // add listener for scanQr button
        binding.scanQr.setOnClickListener(view -> {
            launchScanQrActivity();
        });

        // register contract for new QR Activity
        qrActivityLauncher = registerForActivityResult(new AddNewQrContract(),
                this::receiveAddedQrCode);
        // get player model
        firestorePlayerController.getCompleteCurrentPlayer(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        if(!hidden && player == null){
            firestorePlayerController.getCompleteCurrentPlayer(this);
            showLoadingBar();
        }
    }

    /**
     * Launches the Scan Qr Activity
     */
    private void launchScanQrActivity() {
        qrActivityLauncher.launch(null);
    }

    private void receiveAddedQrCode(String qrHash) {
        HomeFragmentDirections.ActionQrCodeView action = HomeFragmentDirections.actionQrCodeView(qrHash);
        Navigation.findNavController(getView()).navigate(action);
    }

    /**
     * Shows the loading bar
     */
    private void showLoadingBar() {
        binding.progressBarLayout.progressBarBox.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the loading bar
     */
    private void hideLoadingBar() {
        binding.progressBarLayout.progressBarBox.setVisibility(View.GONE);
    }

    /**
     * Called when player object is available
     * @param player the player model
     */
    @Override
    public void getPlayer(Player player) {
        this.player = player;
        updatePlayerInfo();
    }

    /**
     * Updates the player info on the UI
     */
    private void updatePlayerInfo() {
        if (player == null) {
            return;
        }
        binding.playerStats.playerUsername.setText(player.getUsername());
        binding.playerStats.playerScore.setText(String.format(Locale.CANADA, "%d", player.getScore()));
        binding.playerStats.playerQrsScanned.setText(String.format(Locale.CANADA, "%d", player.getQrCodes().size()));
        binding.playerStats.highestQrScore.setText(String.format(Locale.CANADA, "%d", player.getHighestQr()));
        binding.playerStats.lowestQrScore.setText(String.format(Locale.CANADA, "%d", player.getLowestQr()));
        binding.playerStats.playerImage.setImageDrawable(SnapshotController.getDrawableForString(player.retrieveStringToDraw()));
        hideLoadingBar();
    }
}