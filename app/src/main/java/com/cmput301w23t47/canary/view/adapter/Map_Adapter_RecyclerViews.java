package com.cmput301w23t47.canary.view.adapter;

import com.cmput301w23t47.canary.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cmput301w23t47.canary.model.Qrcodem;
import com.cmput301w23t47.canary.view.fragment.FragmentMapScreenMapListSearch;

import java.util.ArrayList;

//
/**
 * A adapter that holds the recycler view for the map list ( fragmentmapscreenmaplistsearch )
 * displays this in the format of a list
 * the xml file which determines how elements are stored is map_list_element_placement
 */
public class Map_Adapter_RecyclerViews extends RecyclerView.Adapter<Map_Adapter_RecyclerViews.ViewHolder>{
    private static final String TAG = "MapQRLocationListAdapter";

    Context context;
    private ArrayList<Qrcodem> mqrCodes = new ArrayList<>();
    private MapRecyclerClickListener mmapQRLocationListAdapterClickListener;

    public Map_Adapter_RecyclerViews(ArrayList<Qrcodem> qrcodes, MapRecyclerClickListener mapRecyclerClickListener) {
        this.mqrCodes = qrcodes;
        mmapQRLocationListAdapterClickListener = mapRecyclerClickListener;
    }

    //could use help dont know what the error is here
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_list_element_placement, parent, false);
        final ViewHolder holder = new ViewHolder(view, mmapQRLocationListAdapterClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).qrname.setText(mqrCodes.get(position).getName());
        ((ViewHolder)holder).qrpoint.setText(mqrCodes.get(position).getScore());
    }


    @Override
    public int getItemCount() {
        return mqrCodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView qrname,qrpoint;

        MapRecyclerClickListener clickListener;

        public ViewHolder(View itemView, MapRecyclerClickListener clickListener) {
            super(itemView);

            // get the text views
            qrname = itemView.findViewById(R.id.qr_name_list);
            qrpoint = itemView.findViewById(R.id.qr_point_total_list);
            // get the image view

            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onMapSelected(getAdapterPosition());
        }
    }

    public interface MapRecyclerClickListener {
        public void onMapSelected(int position);
    }


    public void addQRCode(Qrcodem qrCode) {
        mqrCodes.add(qrCode);
        notifyDataSetChanged();
    }
}
/*
map_list_element_placement.xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_marginTop="10dp"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/qr_image_list"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:layout_marginRight="10dp" />

    <TextView
        android:id="@+id/qr_name_list"
        android:layout_width="219dp"
        android:layout_height="wrap_content"
        android:layout_toEndOf="@+id/qr_image_list"
        android:text="TextView" />

    <TextView
        android:id="@+id/qr_date_found_list"
        android:layout_width="224dp"
        android:layout_height="29dp"
        android:layout_below="@+id/qr_name_list"
        android:layout_toEndOf="@+id/qr_image_list"
        android:text="TextView" />

    <TextView
        android:id="@+id/qr_point_total_list"
        android:layout_width="135dp"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:text="Points"
        android:textSize="24sp" />


</RelativeLayout>

 */