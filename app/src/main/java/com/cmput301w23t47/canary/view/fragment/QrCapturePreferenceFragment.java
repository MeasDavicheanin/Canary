package com.cmput301w23t47.canary.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.MainActivity;
import com.cmput301w23t47.canary.callback.DoesResourceExistCallback;
import com.cmput301w23t47.canary.callback.OperationStatusCallback;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.controller.RandomNameGenerator;
import com.cmput301w23t47.canary.controller.ScoreCalculator;
import com.cmput301w23t47.canary.databinding.FragmentQrCapturePreferenceBinding;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.QrCode;
import com.cmput301w23t47.canary.model.Snapshot;
import com.cmput301w23t47.canary.view.contract.AddNewQrContract;
import com.cmput301w23t47.canary.view.contract.SnapshotContract;

import java.util.Date;
import java.util.Locale;

/**
 * Continues without saving location if permission denied
 */
public class QrCapturePreferenceFragment extends Fragment implements 
        DoesResourceExistCallback, OperationStatusCallback {
    public static final String TAG = "QrCapturePreferenceFragment";

    private FragmentQrCapturePreferenceBinding binding;
    AlertDialog.Builder builder;
    private ActivityResultLauncher<Object> snapshotActivityLauncher;

    // controller for making qr related queries
    private final FirestorePlayerController firestorePlayerController = new FirestorePlayerController();

    private boolean saveLocation = true;
    private final QrCode qrCode = new QrCode();

    public QrCapturePreferenceFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQrCapturePreferenceBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qrCode.setHash(QrCapturePreferenceFragmentArgs.fromBundle(getArguments()).getQrHash());
        firestorePlayerController.doesCurrentPlayerHaveQr(qrCode.getHash(), this);
        binding.saveLocationCheckbox.setChecked(saveLocation);
    }

    void init() {
        binding.saveLocationCheckbox.setOnClickListener(view -> {
            saveLocation = binding.saveLocationCheckbox.isChecked();
        });
        snapshotActivityLauncher = registerForActivityResult(new SnapshotContract(), this::receiveSnapshot);

        binding.takeSnap.setOnClickListener(view -> {
            captureSnapshot();
        });
        binding.noSnap.setOnClickListener(view -> {
            persistQr(null);
        });

        builder = new AlertDialog.Builder(getContext());
        showLoadingBar();
    }

    /**
     * Shows the loading bar
     */
    private void showLoadingBar() {
        binding.progressBarBox.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the loading bar
     */
    private void hideLoadingBar() {
        binding.progressBarBox.setVisibility(View.GONE);
    }

    @Override
    public void doesResourceExists(boolean exists) {
        hideLoadingBar();
        if (exists) {
            // if qr with the given hash exist, show an alert
            builder.setMessage("The selected QR is deleted")
                    .setTitle("QR Code Deleted")
                    .setCancelable(false)
                    .setPositiveButton("Continue", (DialogInterface dialog, int id) -> {
                        // TODO: handle already scanned QR
                        returnToQrCodePage();
                    }).create().show();
        } else {
            RandomNameGenerator nameGenerator = new RandomNameGenerator();
            qrCode.setName(nameGenerator.getWord());
            qrCode.setScore(ScoreCalculator.calculateScore(qrCode.getHash()));
            updateUi();
        }
    }

    public void returnToQrCodePage() {
        Intent intent = new Intent();
        intent.putExtra(AddNewQrContract.RESPONSE_TAG, qrCode.getHash());
        Activity activity = getActivity();
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    /**
     * Updates the UI
     */
    private void updateUi() {
        binding.qrName.setText(qrCode.getName());
        binding.qrScore.setText(String.format(Locale.CANADA, "%d", qrCode.getScore()));
    }

    private void receiveSnapshot(Bitmap image) {
        showLoadingBar();
        persistQr(image);
    }

    /**
     * Persists the scanned QR to db
     * @param snapshot The snapshot of qr
     */
    private void persistQr(Bitmap snapshot) {
        PlayerQrCode playerQrCode = new PlayerQrCode(qrCode, new Date());
        // TODO: Get location
        if (snapshot != null) {
            playerQrCode.setSnapshot(new Snapshot(snapshot));
        }
        firestorePlayerController.addQrToCurrentPlayer(playerQrCode, this);
    }

    private void captureSnapshot() {
        snapshotActivityLauncher.launch(null);
    }

    @Override
    public void operationStatus(boolean status) {
        // QR Persisted, go back
        returnToQrCodePage();
    }
}