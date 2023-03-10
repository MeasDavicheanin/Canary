package com.cmput301w23t47.canary.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.Player;

public class PlayerListArrayAdapter extends ArrayAdapter<Player> {

    public PlayerListArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
