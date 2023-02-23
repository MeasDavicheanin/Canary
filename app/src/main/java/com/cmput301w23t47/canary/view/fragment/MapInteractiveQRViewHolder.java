package com.cmput301w23t47.canary.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t47.canary.R;

/**
 * Class for MapInteractiveQRViewHolder
 * This is for the fragment screen the adapter and the will be somewhere else
 */
public class MapInteractiveQRViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView qrtextview;
    TextView qrtextview2;
    TextView qrtextview3;


    public MapInteractiveQRViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.qr_image_list);
        qrtextview = itemView.findViewById(R.id.qr_name_list);
        qrtextview2 = itemView.findViewById(R.id.qr_date_found_list);
        qrtextview3 = itemView.findViewById(R.id.qr_point_total_list);
    }



}
