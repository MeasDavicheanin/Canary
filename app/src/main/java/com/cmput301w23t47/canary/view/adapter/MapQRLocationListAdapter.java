package com.cmput301w23t47.canary.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t47.canary.model.PlayerQRCode;
import com.cmput301w23t47.canary.model.QRCode;

import java.util.List;

/**
 * Class for the list of QR Codes on map
 * should have multiple accessible qr codes
 *
 */
public class MapQRLocationListAdapter extends RecyclerView.Adapter<MapQRLocationListAdapter.ViewHolder>{

    private List<PlayerQRCode> mQRList;
    //This is the constructor for the MapQRLocationListAdapter
    //the argument is a list of QRCode objects
    //so pass the world qr list
    public MapQRLocationListAdapter(List<QRCode> QRList) {
        mQRList = QRList;
    }


    @Override
    public MapQRLocationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mQRList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

            //Here we will need to set the textviews to the correct values
            //I dont know what the qr qill be yet though
        }
    }



}
