package com.example.gslcmobile;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    int id;
    LatLng pinPoint;

    public Location(int id, LatLng pinPoint) {
        this.id = id;
        this.pinPoint = pinPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getPinPoint() {
        return pinPoint;
    }

    public void setPinPoint(LatLng pinPoint) {
        this.pinPoint = pinPoint;
    }
}