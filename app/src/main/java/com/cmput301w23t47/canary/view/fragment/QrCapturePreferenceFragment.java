package com.cmput301w23t47.canary;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class QrDetails extends Fragment {

    static void takeSnapshot() {

    }

    static void noSnapshot() {

    }

    public QrDetails () {
        super(R.layout.qr_details);
    }

    private Button takeSnap;
    private Button noSnap;
    private CompoundButton checkBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.qr_details, null);
        EditText qrName = view.findViewById(R.id.qr_name);
        EditText qrScore = view.findViewById(R.id.qr_score);

        takeSnap.setOnClickListener(v -> {
            takeSnapshot();
        });
        noSnap.setOnClickListener(v -> {
            noSnapshot();
        });


    }
    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

    }
}
