package com.cmput301w23t47.canary.view.fragment;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.Manifest;

//import com.cmput301w23t47.canary.Manifest;
import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.view.adapter.Map_Adapter_RecyclerViews;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.firebase.firestore.auth.QRCodeProvider;

import java.util.ArrayList;

import static com.cmput301w23t47.canary.Constants.MAPVIEW_BUNDLE_KEY;
//User_list_fragment
/**
 *  A fragment that holds a mapview and a recyckler view
 *  The mapview is used to display the location of the qr codes
 *  The recycler view is used to display the list of qr codes
 *  The recycler view is also used to display the map results
 *
 *
 */
public class FragmentMapScreenMapListSearch extends Fragment implements OnMapReadyCallback {

    //widgets
    private static final String TAG = "Map_Activity_Screen_map_and_list";
    //widgets
    private RecyclerView mMapListRecyclerView;
    private MapView mMapView;


    //vars
    // this isn't needed just pull the qr code global list.
    // private ArrayList<User> mMapList = new ArrayList<>();

    private Map_Adapter_RecyclerViews mMapAdapterRecyclerViews;


    public static FragmentMapScreenMapListSearch newInstance() {
        return new FragmentMapScreenMapListSearch();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // this is supposed to be something else but I don't know what
            // I think it has something to do with our firestore
            //mMapList = getArguments().getParcelableArrayList(getString(R.string.intent_recycler_list_map));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_screen_map_and_list, container, false);
        mMapListRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        mMapView = view.findViewById(R.id.map_display);

        initMapListRecyclerView();
        initGoogleMap(savedInstanceState);

        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    private void initMapListRecyclerView() {
        mMapAdapterRecyclerViews = new Map_Adapter_RecyclerViews(mMapList);
        mMapListRecyclerView.setAdapter(mMapAdapterRecyclerViews);
        mMapListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void qrrecycler(){

    }

}
/*
this needs to be implemented in other activities doesn't matter which one so long as they are asked first
Warning need to implement things like:
    getting the location of the user
    permissions for location
    getting the location of the QR code

 */




/*
FRAGMENT NAME:
Map_Activity_Screen_map_and_list_view

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/White">

    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:weightSum="100"
        android:background="@color/White">

        <android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="50"
            android:id="@+id/QR_list_recycler_view">

        </android.support.v7.widget.RecyclerView>

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="50"
            android:id="@+id/map_container">

            <com.google.android.gms.maps.MapView
                xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:id="@+id/map_display" />
        </RelativeLayout>


    </LinearLayout>

    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/progressBar"
        android:layout_centerInParent="true"
        android:visibility="gone"/>

</RelativeLayout>



 */