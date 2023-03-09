package com.cmput301w23t47.canary.model;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;


/**
 * This class is used to store the location of a player
 *
 */
public class UserLocation {
    private Player user;
    private GeoPoint geo_point;
    private @ServerTimestamp Date timestamp;

    public UserLocation(Player user, GeoPoint geo_point, Date timestamp) {
        this.user = user;
        this.geo_point = geo_point;
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

    public GeoPoint getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
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
                ", geo_point=" + geo_point +
                ", timestamp=" + timestamp +
                '}';
    }
}
