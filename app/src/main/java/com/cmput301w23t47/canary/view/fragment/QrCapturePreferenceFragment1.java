package com.cmput301w23t47.canary.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput301w23t47.canary.databinding.FragmentQrCapturePreferenceBinding;


public class QrCapturePreferenceFragment1 extends Fragment {
    private FragmentQrCapturePreferenceBinding binding;

    static void takeSnapshot() {

    }

    static void noSnapshot() {

    }

    public QrCapturePreferenceFragment1() {}

    private Button takeSnap;
    private Button noSnap;
    private CompoundButton checkBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
//        init();
//        return binding.getRoot();
        binding = FragmentQrCapturePreferenceBinding.inflate(inflater, container, false);
        return binding.getRoot();

//        takeSnap.setOnClickListener(v -> {
//            takeSnapshot();
//        });
//        noSnap.setOnClickListener(v -> {
//            noSnapshot();
//        });
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
    }
}
