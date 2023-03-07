package com.cmput301w23t47.canary.view.adapter;

import com.cmput301w23t47.canary.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t47.canary.view.fragment.MapInteractiveQRViewHolder;
import com.cmput301w23t47.canary.viewmodel.QRCodeVMElement;

import java.util.ArrayList;

/**
 * Class for the list of QR Codes on map
 * should have multiple accessible qr codes
 *
 */
public class Map_Adapter_RecyclerViews extends RecyclerView.Adapter<MapInteractiveQRViewHolder>{
    private static final String TAG = "MapQRLocationListAdapter";

    Context context;
    private ArrayList<QRCodeVMElement> mqrCodes = new ArrayList<>();
    private MapRecyclerClickListener mmapQRLocationListAdapterClickListener;

    public Map_Adapter_RecyclerViews(ArrayList<QRCodeVMElement> qrcodes, MapRecyclerClickListener mapRecyclerClickListener) {
        this.mqrCodes = qrcodes;
        mmapQRLocationListAdapterClickListener = mapRecyclerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_list_element_placement, parent, false);
        final ViewHolder holder = new ViewHolder(view, mmapQRLocationListAdapterClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapInteractiveQRViewHolder holder, int position) {
        ((ViewHolder)holder).MapTitle.setText(mqrCodes.get(position).getQRname());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).MapTitle.setText(mqrCodes.get(position).getQRname());
    }

    @Override
    public int getItemCount() {
        return mqrCodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView MapTitle;
        MapRecyclerClickListener clickListener;

        public ViewHolder(View itemView, MapRecyclerClickListener clickListener) {
            super(itemView);
            MapTitle = itemView.findViewById(R.id.map_title);
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
}
