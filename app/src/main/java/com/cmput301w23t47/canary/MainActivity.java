package com.cmput301w23t47.canary;

import static com.cmput301w23t47.canary.Constants.ERROR_DIALOG_REQUEST;
import static com.cmput301w23t47.canary.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.cmput301w23t47.canary.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;

import com.cmput301w23t47.canary.controller.NavbarController;
import com.cmput301w23t47.canary.databinding.ActivityMainBinding;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.model.UserLocation;
import com.cmput301w23t47.canary.view.fragment.HomeFragment;
import com.cmput301w23t47.canary.view.fragment.LeaderboardFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.auth.FirebaseAuth;




/**
 * Main Acitvity
 * @author Meharpreet Singh Nanda
 */
public class MainActivity extends AppCompatActivity implements
//        UpdatePlayerCallback,
        //View.OnClickListener,
        NavbarController.NavigateToPage {

    private ActivityMainBinding binding;
    int i = 0;


    private HomeFragment homeFragment;
    private LeaderboardFragment leaderboardFragment;
    private Fragment activeFragment;


    //Map permissions - location,gps, and device location
    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private UserLocation mUserLocation;

    // TODO: Get the actual Playername
    private String playerPlayername = "jamesk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    /**
     * Initialization for activity
     */
    private void init(Bundle savedInstanceState) {
        initFragmentStack();
        initNavbar();
    }

    /**
     * Initializes the fragment stack
     */
    private void initFragmentStack() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            leaderboardFragment = new LeaderboardFragment(playerPlayername);
        }
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, homeFragment, HomeFragment.TAG)
                .add(R.id.fragment_container_view, leaderboardFragment, LeaderboardFragment.TAG)
                .hide(leaderboardFragment)
                .commit();
        activeFragment = homeFragment;
    }

    /**
     * Initializes the navbar
     */
    private void initNavbar() {
        // add listener for navbar
        binding.bottomNavigationLayout.bottomNavigation.setOnItemSelectedListener(item -> {
            NavbarController.handleSelection(item, this);
            return true;
        });
        // ignore if option reselected
        binding.bottomNavigationLayout.bottomNavigation.setOnItemReselectedListener(item -> {});
        // configure selected option; calls the itemSelectedCallback function
        binding.bottomNavigationLayout.bottomNavigation.setSelectedItemId(R.id.page_home);
    }

    /**
     * Navigates to the leaderboard page
     */
    @Override
    public void navigateToHome() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(activeFragment)
                .show(homeFragment)
                .commit();
        activeFragment = homeFragment;
    }


    @Override
    public void navigateToSearch() {

    }

    /**
     * Navigates to the leaderboard page
     */
    @Override
    public void navigateToLeaderboard() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(activeFragment)
                .show(leaderboardFragment)
                .commit();
        activeFragment = leaderboardFragment;
    }

    @Override
    public void navigateToPlayers() {

    }

    @Override
    public void navigateToProfile() {

    }



    //Start of the permission code as well as the location code

    /**
     * Checks if the device has the correct services to run the map
     * placed in the onResume method because harder to avoid it
     */
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getPlayerDetails();
            }
            else{
                getLocationPermission();
            }
        }
    }

    private void getPlayerDetails(){
        if(mUserLocation == null){
            mUserLocation = new UserLocation();
            DocumentReference PlayerRef = mDb.collection("Player")
                    .document(FirebaseAuth.getInstance().getUid());

            PlayerRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: successfully set the Player client.");
                        Player Player = task.getResult().toObject(Player.class);
                        mUserLocation.setPlayer(Player);
                        getLastKnownLocation();
                    }
                }
            });
        }
        else{
            getLastKnownLocation();
        }
    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    mUserLocation.setGeo_point(geoPoint);
                    mUserLocation.setTimestamp(null);
                    //savePlayerLocation();
                }
            }
        });

    }


    /*
     AGAINST PRIVACY POLICY
     DO NOT USE
     */
//    private void savePlayerLocation(){
//
//        if(mUserLocation != null){
//            DocumentReference locationRef = mDb
//                    .collection(getString(R.string.collection_Player_locations))
//                    .document(FirebaseAuth.getInstance().getUid());
//
//            locationRef.set(mUserLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Log.d(TAG, "savePlayerLocation: \ninserted Player location into database." +
//                                "\n latitude: " + mUserLocation.getGeo_point().getLatitude() +
//                                "\n longitude: " + mUserLocation.getGeo_point().getLongitude());
//                    }
//                }
//            });
//        }
//    }

    /**
     * check if the user has accepted the map permissions
     * @return
     */
    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    /**
     * Prompt the user to enable the gps on their app
     * if they say no, then the app will not work
     * if they say yes, then the app will open the settings page, so they can enable it
     */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * check if the user has accepted the gps location permission
     * @return
     * true if the user has accepted the gps location permission
     * false ask the user to accept the gps location permission
     */
    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getChatrooms();
            getPlayerDetails();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * check if the user has accepted the google play services
     * this will allow the use of the google map
     * If not then the app will open a dialog box to allow the user to get the google play services
     * @return
     * true if the user has accepted the google play services and they are working
     * false if the user has not accepted the google play services or if they are not working
     */
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the Player can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     *
     * @param requestCode The request code passed in {@link //#requestPermissions(
     * android.app.Activity, String[], int)}
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
       // this was added in because otherwise it gives error
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    //getChatrooms();
                    getPlayerDetails();
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }


}