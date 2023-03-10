package com.cmput301w23t47.canary.model;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;


/**
 * This class is used to store the location of a player
 *
 */
public class UserLocation {
    private Player user;
    private Location loca;
    private @ServerTimestamp Date timestamp;

    public UserLocation(Player user,Location loca, Date timestamp) {
        this.user = user;
        this.loca = loca;
        this.timestamp = timestamp;
    }

    public UserLocation() {

    }

    public Player getPlayer() {
        return user;
    }

    public void setPlayer(Player user) {
        this.user = user;
    }

    public Location getLocation() {
        return loca;
    }

    public void setLocation(Location loca) {
        this.loca = loca;
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
}
