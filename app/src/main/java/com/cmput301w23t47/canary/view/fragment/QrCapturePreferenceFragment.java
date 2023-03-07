package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.databinding.FragmentQrCapturePreferenceBinding;

public class QrCapturePreferenceFragment extends Fragment {
    private FragmentQrCapturePreferenceBinding binding;

    private String qrHash;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qrHash = QrCapturePreferenceFragmentArgs.fromBundle(getArguments()).getQrHash();
        binding.qrName.setText(qrHash);
    }
}