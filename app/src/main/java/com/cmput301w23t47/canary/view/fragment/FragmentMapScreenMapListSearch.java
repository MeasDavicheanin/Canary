package com.cmput301w23t47.canary.view.fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.Manifest;
import android.location.Location;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.callback.RecyclerViewInterface;
import com.cmput301w23t47.canary.model.Qrcodem;
import com.cmput301w23t47.canary.model.UserLocation;
import com.cmput301w23t47.canary.model.WorldQRLIST;
import com.cmput301w23t47.canary.view.adapter.Map_Adapter_RecyclerViews;
import com.cmput301w23t47.canary.view.adapter.ViewWeightAnimationWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
public class FragmentMapScreenMapListSearch extends Fragment implements OnMapReadyCallback, View.OnClickListener, RecyclerViewInterface {

    //constants
    private static final String TAG = "Map_Activity_Screen_map_and_list";
    //update the location every 5 seconds
    private static final int LOCATION_UPDATE_INTERVAL = 5000;
    private static final int MAP_LAYOUT_STATE_CONTRACTED =0;
      private static final int MAP_LAYOUT_STATE_EXPANDED =1;
      
    
    //base list
    //this is the list that holds all the qr codes
    private ArrayList<Qrcodem> mglobalQRList = new WorldQRLIST().getQrList();
    //this is the list that holds the qr codes that are displayed
    private ArrayList<Qrcodem> mSearchResults = new ArrayList<>();
    private ArrayList<Qrcodem> mSearchResultsCopy = new ArrayList<>();

    
    // recylerviews and lists
    private RecyclerView mMapListRecyclerView;
    private Map_Adapter_RecyclerViews mMapAdapterRecyclerViews;
    private Map_Adapter_RecyclerViews.MapRecyclerClickListener mMapRecyclerClickListener;

    //search bar
    private UserLocation mUserLocation;
    private AutoCompleteTextView mSearchBar;
    private ArrayAdapter<String> mSearchRangeAdapter;
    private String[] mSearchRange = {"200m", "500m", "1km", "2km", "5km", "NO LIMIT" };
    private double distance;
    private double LatituteQrCodeLocation;
    private double LongitudeQrCodeLocation;
    private Location QRCodeLocation;
    private int mSearchRangeDouble;
    private String mSearchSpecifiedRange;
    private AutoCompleteTextView mSearchBarRange;
    
    
    //Map
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    
    private LatLngBounds mMapBoundary;
    private UserLocation mUserPosition;
    //this is for the updates
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private RelativeLayout mMapContainer;
    private int mMapLayoutState = 0;
    
    
    public static FragmentMapScreenMapListSearch newInstance() {
        return new FragmentMapScreenMapListSearch();
    }
    


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // this is supposed to be something else but I don't know what
            // I think it has something to do with our firestore
            mSearchResults = getArguments().getParcelableArrayList(getString(R.string.intent_recycler_list_map));
    
            initDropDownMenuItems();
            initMapSearchRangeCheck();
         
            
        }

    }
    
    //Change activity if item is clicked
    @Override
    public void onItemClicked(int position){
        // Need to change the error with the qr activitiy
        Intent intent = new Intent(getActivity(), (QRCODEACTIVITYIDONTKNOWWHERETHISIS) );
        
        // if you want to send the whole qr code then use this one
        intent.putExtra(getString(R.string.intent_qr_code), mSearchResults.get(position));
        
        //if you want to break up the intents into seperate data then use this one
        //intent.putExtra(getString(R.string.intent_qr_name), mSearchResults.get(position).getName());
        // intent.putExtra( getString(R.string.intent_qr_location), mSearchResults.get(position).getLocation());
        // intent.putExtra( getString(R.string.intent_qr_hash), mSearchResults.get(position).getQrHash());
       //  intent.putExtra( getString(R.string.intent_qr_score), mSearchResults.get(position).getScore());
        
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_screen_map_and_list, container, false);
        mMapListRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        mMapView = view.findViewById(R.id.map_display);
        mMapContainer = view.findViewById(R.id.map_container);
        
        view.findViewById( R.id.btn_full_screen_map).setOnClickListener(this);

        initMapListRecyclerView();
        initGoogleMap(savedInstanceState);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        startUserLocationsRunnable();
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
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void qrrecycler(){

    }
    
    
    
    
    
    //map methods
    private void setCameraView(){
    
        double bottomBoundary = mUserPosition.getLatitude() - .1;
        double leftBoundary = mUserPosition.getLongitude() - .1;
        double topBoundary = mUserPosition.getLatitude() + .1;
        double rightBoundary = mUserPosition.getLongitude() + .1;
    
        mMapBoundary = new LatLngBounds(
              new LatLng(bottomBoundary, leftBoundary),
              new LatLng(topBoundary, rightBoundary)
        );
    
        mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));
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
        
        //this is where to set the list of the users location
        for (int i = 0; i < mSearchResultsCopy.size(); i++) {
            map.addMarker(new MarkerOptions()
                  .position(new LatLng(mSearchResultsCopy.get(i).getLocation().getLatitude(), mSearchResultsCopy.get(i).getLocation().getLongitude()))
                  .title( mSearchResults.get(i).getScoreString() ));
            
        }
        
        
        mGoogleMap = map;
        setCameraView();
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
    
    
    
    //init methods
    /**
     * This is a temporary proof of concept function
     */
    public void initMapSearchRangeCheck(){
        mglobalQRList = new WorldQRLIST().getQrList();
        mSearchBarRange = (AutoCompleteTextView) getActivity().findViewById(R.id.map_search_range_dropdown_menu);
        mSearchSpecifiedRange = mSearchBarRange.getText().toString();
        mSearchResultsCopy.clear();
        
        switch(mSearchSpecifiedRange){
            case "200m":
                mSearchRangeDouble = 200;
                break;
            case "500m":
                mSearchRangeDouble = 500;
                break;
            case "1km":
                mSearchRangeDouble = 1000;
                break;
            case "2km":
                mSearchRangeDouble = 2000;
                break;
            case "5km":
                mSearchRangeDouble = 5000;
                break;
            case "NO LIMIT":
                mSearchRangeDouble = 10000;
                break;
            default:
                mSearchRangeDouble = 100;
                break;
        }
        
        
        for(int i = 0; i < mglobalQRList.size(); i++){
            
            LatituteQrCodeLocation = mglobalQRList.get(i).getLocation().getLatitude();
            LongitudeQrCodeLocation = mglobalQRList.get(i).getLocation().getLongitude();
            QRCodeLocation.setLatitude(LatituteQrCodeLocation);
            QRCodeLocation.setLongitude(LongitudeQrCodeLocation);
            
            distance = mUserLocation.getLocation().distanceTo(QRCodeLocation);
            if(distance <= mSearchRangeDouble || mSearchRangeDouble == 10000){
                
                mSearchResultsCopy.add(mglobalQRList.get(i));
                //one of these isn't needed I dont know which one
                mMapAdapterRecyclerViews.addQRCode(mglobalQRList.get(i));
                // though I assume that it is this one
                //mSearchResults.add(mglobalQRList.get(i));
            }
            
        }
        
    }
    private void initMapListRecyclerView() {
        mMapAdapterRecyclerViews = new Map_Adapter_RecyclerViews(mSearchResults,this);
        mMapListRecyclerView.setAdapter(mMapAdapterRecyclerViews);
        mMapListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void initDropDownMenuItems(){
        mSearchBar = (AutoCompleteTextView) getActivity().findViewById(R.id.map_search_range_dropdown_menu);
        mSearchRangeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mSearchRange);
        
        mSearchBar.setAdapter(mSearchRangeAdapter);
        mSearchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }
        });
    }
    

    
    //clicking on a map marker
    //not needed this would cause a marekr dialog to open not
//    public void OnInfoClickListener(){
//        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Log.d(TAG, "onInfoWindowClick: clicked on marker");
//                Toast.makeText(getActivity(), "clicked on marker", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    
    
    // Real Time Updates
    private void startUserLocationsRunnable(){
        Log.d(TAG, "startUserLocationsRunnable: starting runnable for retrieving updated locations.");
        mHandler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                initMapSearchRangeCheck();
                mHandler.postDelayed(mRunnable, LOCATION_UPDATE_INTERVAL);
            }
        }, LOCATION_UPDATE_INTERVAL);
    }
    
    private void stopLocationUpdates(){
        mHandler.removeCallbacks(mRunnable);
    }
    
    //change the size of the map
    
    
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //did you click on the button to make it full screen
            case R.id.btn_full_screen_map:
                if(mMapLayoutState == MAP_LAYOUT_STATE_CONTRACTED){
                    expandMapAnimation();
                    mMapLayoutState = MAP_LAYOUT_STATE_EXPANDED;
                }else{
                    contractMapAnimation();
                    mMapLayoutState = MAP_LAYOUT_STATE_CONTRACTED;
                }
                break;
                
        }
    }
    
    private void expandMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
              "weight",
              55,
              108);
        mapAnimation.setDuration(800);
    
        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mMapListRecyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
              "weight",
              35,
              0);
        recyclerAnimation.setDuration(800);
    
        recyclerAnimation.start();
        mapAnimation.start();
    }
    
    private void contractMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
              "weight",
              108,
              55);
        mapAnimation.setDuration(800);
        
        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mMapListRecyclerView);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
              "weight",
              0,
              35);
        recyclerAnimation.setDuration(800);
        
        recyclerAnimation.start();
        mapAnimation.start();
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