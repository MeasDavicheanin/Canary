package com.cmput301w23t47.canary.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;


/**
 * This class is used to store the location of a player
 *
 */
public class UserLocation implements Parcelable {
    private Player user;
    private Location loca;
    private GeoPoint geopoint;
    private @ServerTimestamp Date timestamp;

    public UserLocation(Player user,Location loca, Date timestamp) {
        this.user = user;
        this.loca = loca;
        this.geopoint = new GeoPoint(loca.getLatitude(), loca.getLongitude());
        this.timestamp = timestamp;
    }

    public UserLocation() {

    }
    
    protected UserLocation(Parcel in) {
        user = in.readParcelable(Player.class.getClassLoader());
    }
    
    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }
        
        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };
    
    public Player getPlayer() {
        return user;
    }

    public void setPlayer(Player user) {
        this.user = user;
    }

    public Location getLocation() {
        return loca;
    }
    
    public double getLatitude() {
        return loca.getLatitude();
    }
    
    public double getLongitude() {
        return loca.getLongitude();
    }
    
    public GeoPoint getGeoPoint() {
        return geopoint;
    }
    
      public void setGeoPoint(GeoPoint geoPoint) {
         this.loca = new Location("");
         this.loca.setLatitude(geoPoint.getLatitude());
         this.loca.setLongitude(geoPoint.getLongitude());
         this.geopoint = geoPoint;
      }
      

    public void setLocation(Location loca) {
        this.loca = loca;
        this.geopoint = new GeoPoint(loca.getLatitude(), loca.getLongitude());
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "user=" + user +
                ", loca=" + loca +
                ", timestamp=" + timestamp +
                '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }
}
