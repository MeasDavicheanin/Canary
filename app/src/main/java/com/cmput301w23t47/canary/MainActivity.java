package com.cmput301w23t47.canary;

import static com.cmput301w23t47.canary.Constants.ERROR_DIALOG_REQUEST;
import static com.cmput301w23t47.canary.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.cmput301w23t47.canary.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cmput301w23t47.canary.databinding.ActivityMainBinding;
import com.cmput301w23t47.canary.view.fragment.HomeFragment;
import com.cmput301w23t47.canary.view.fragment.LeaderboardFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;



/**
 * Main Acitvity
 * @author Meharpreet Singh Nanda
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    
    private HomeFragment homeFragment;
    private LeaderboardFragment leaderboardFragment;
    private Fragment activeFragment;
    
    
    //Map permissions - location,gps, and device location
    //private FirebaseFirestore mDb;
    //private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private boolean mLocationPermissionGranted = false;
//    private FusedLocationProviderClient mFusedLocationClient;
//    private UserLocation mUserLocation;
    
    // TODO: Get the actual Playername
    private String playerPlayername = "jamesk";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient( this );
        //mDb = FirebaseFirestore.getInstance();
        
        init( savedInstanceState );
    }
    
    /**
     * Initialization for activity
     */
    private void init(Bundle savedInstanceState) {
        initNavbar();
    }
    
    /**
     * Initializes the navbar (bottom and top)
     */
    private void initNavbar() {
        // Bottom navigation
        NavController navController = Navigation.findNavController( this, R.id.fragment_container_view_main );
        NavigationUI.setupWithNavController( binding.bottomNavigationLayout.bottomNavigation, navController );
    
        // top navigation
        setSupportActionBar( binding.toolbar );
        appBarConfiguration = new AppBarConfiguration.Builder( navController.getGraph() ).build();
        NavigationUI.setupActionBarWithNavController( this, navController, appBarConfiguration );
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.fragment_container_view_main );
        return NavigationUI.navigateUp( navController, appBarConfiguration )
              || super.onSupportNavigateUp();
    }
    
    
    //Start of the permission code
    
    /**
     * Checks if the device has the correct services to run the map
     * placed in the onResume method because harder to avoid it
     */
    protected void onResume() {
        super.onResume();
        if ( checkMapServices() ) {
            if ( mLocationPermissionGranted != true) {
                getLocationPermission();
            }
        }
    }
    
    /**
     * check Who is using the app and try to match them with the database of players
     * ie if the user1 is logged in then get the user1 details from the database
     * and store the location with the user1 details
     * if the user is not logged in then just get the last known location
     * storing it in an empty class
     */
    
    /**
     * check if the user has accepted the map permissions
     * @return true if they have
     * false if they have not
     */
    private boolean checkMapServices() {
        if ( isServicesOK() ) {
            if ( isMapsEnabled() ) {
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
        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "This application requires GPS to work properly, do you want to enable it?" )
              .setCancelable( false )
              .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                  public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                      Intent enableGpsIntent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                      startActivityForResult( enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS );
                  }
              } );
        final AlertDialog alert = builder.create();
        alert.show();
    }
    
    /**
     * check if the user has accepted the gps location permission
     *
     * @return true if the user has accepted the gps location permission
     * false ask the user to accept the gps location permission
     */
    public boolean isMapsEnabled() {
        final LocationManager manager = ( LocationManager ) getSystemService( Context.LOCATION_SERVICE );
    
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
    
        if ( ContextCompat.checkSelfPermission( this.getApplicationContext(),
              android.Manifest.permission.ACCESS_FINE_LOCATION )
              == PackageManager.PERMISSION_GRANTED ) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions( this,
                  new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                  PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION );
        }
    }
    
    /**
     * check if the user has accepted the google play services
     * this will allow the use of the google map
     * If not then the app will open a dialog box to allow the user to get the google play services
     *
     * @return true if the user has accepted the google play services and they are working
     * false if the user has not accepted the google play services or if they are not working
     */
    public boolean isServicesOK() {
        Log.d( TAG, "isServicesOK: checking google services version" );
    
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable( MainActivity.this );
    
        if ( available == ConnectionResult.SUCCESS ) {
            //everything is fine and the Player can make map requests
            Log.d( TAG, "isServicesOK: Google Play Services is working" );
            return true;
        } else if ( GoogleApiAvailability.getInstance().isUserResolvableError( available ) ) {
            //an error occured but we can resolve it
            Log.d( TAG, "isServicesOK: an error occured but we can fix it" );
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog( MainActivity.this, available, ERROR_DIALOG_REQUEST );
            dialog.show();
        } else {
            Toast.makeText( this, "You can't make map requests", Toast.LENGTH_SHORT ).show();
        }
        return false;
    }
    
    /**
     * Callback received when a permissions request has been completed.
     *
     * @param requestCode  The request code passed in {@link //#requestPermissions(
     *                     android.app.Activity, String[], int)}
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // this was added in because otherwise it gives error
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        mLocationPermissionGranted = false;
        switch ( requestCode ) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ( grantResults.length > 0
                      && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
    
    /**
     * This method is called when the child activity exits.
     * and then it will display them on the map
     *
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        Log.d( TAG, "onActivityResult: called." );
        switch ( requestCode ) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if ( mLocationPermissionGranted != true ) {
                    getLocationPermission();
                }
            }
        }
        
    }
    
}



// This is old code for determining the location of the user
// deleted because it is in the wrong place
// and on second thought irrelevent to our task

//    public Location getUserLocation() {
//
//        if ( mUserLocation != null ) {
//            return mUserLocation.getLocation();
//        }
//        return getLastKnownLocation();
//
//    }
//    private void getPlayerDetails() {
//        //get the current user from the database
//        if ( mUserLocation == null ) {
//            mUserLocation = new UserLocation();
//
//            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
//                DocumentReference PlayerRef = mDb.collection( "Player" )
//                      .document( FirebaseAuth.getInstance().getUid() );
//
//                PlayerRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if ( task.isSuccessful() ) {
//                            Log.d( TAG, "onComplete: successfully set the Player client." );
//                            Player Player = task.getResult().toObject( Player.class );
//                            mUserLocation.setPlayer( Player );
//                            getLastKnownLocation();
//                        }
//                    }
//                } );
//            }else{
//                  //if the user is not logged in then just get the last known location
//                // this makes the app work for the emulator
//                // but also makes it kind of pointless to use the UserLocation class
//                  getLastKnownLocation();
//            }
//        } else {
//            getLastKnownLocation();
//        }
//    }
//
//      /**
//      * Gets the last known location of the user
//      * this is done by storing and then getting the last known location
//       */
//    private Location getLastKnownLocation() {
//        Log.d( TAG, "getLastKnownLocation: called." );
//
//
//        if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//            return null;
//        }
//
//        //code wont work if the fused location client is null
//        if(mFusedLocationClient != null) {
//            Log.d( TAG, "getLastKnownLocation: mFusedLocationClient is not null");
//            //does as expected which is get the last known location
//            mFusedLocationClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    if ( task.isSuccessful() ) {
//                        Location location = task.getResult();
//                        mUserLocation.setGeoPoint( new GeoPoint( location.getLatitude(), location.getLongitude() ) );
//                        mUserLocation.setTimestamp( null );
//                        //savePlayerLocation();
//                    }
//                }
//            } );
//        } else {
//            //should do the same thing as the code above just slower
//            Log.d( TAG, "getLastKnownLocation: mFusedLocationClient is null" );
//            // this is the location manager it is a system service that is used to get the location
//            LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//            //turn that location into a location object
//            Location location = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
//            Log.d( TAG, "LocationManager: " + location.getLatitude() + " " + location.getLongitude());
//            //set the location of the user
//            mUserLocation.setGeoPoint( new GeoPoint( location.getLatitude(), location.getLongitude() ) );
//            Log.d( TAG, "getLastKnownLocation: " + mUserLocation.getGeoPoint().getLatitude() + " " + mUserLocation.getGeoPoint().getLongitude() );
//        }
//
//        return mUserLocation.getLocation();
//    }

