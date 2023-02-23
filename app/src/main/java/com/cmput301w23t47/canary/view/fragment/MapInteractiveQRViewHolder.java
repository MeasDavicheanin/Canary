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

    public ImageView imageView;
    public TextView qrtextname;
    public TextView qrtextdate;
    public TextView qrtextpoint;


    public MapInteractiveQRViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.qr_image_list);
        qrtextname = itemView.findViewById(R.id.qr_name_list);
        qrtextdate = itemView.findViewById(R.id.qr_date_found_list);
        qrtextpoint = itemView.findViewById(R.id.qr_point_total_list);
    }



}
