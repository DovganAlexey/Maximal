package com.enzo.testaufgabe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by enzo on 13.04.18.
 */

public class Address implements Serializable {
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("suite")
    @Expose
    private String suite;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("geo")
    @Expose
    private LatLong geo;

    private String fullAddress;
    private String coordinates;

    public Address(String fullAddress, String zipcode, String coords) {
        this.fullAddress = fullAddress;
        this.zipcode = zipcode;
        this.coordinates = coords;
    }

    public String getFullAddress() {
        if (fullAddress == null)
            fullAddress = street + ", " + suite + ", " + city;
        return fullAddress;
    }

    public String getCoordinates() {
        if (coordinates == null)
            coordinates = "(" + geo.getLat() + ", " + geo.getLng() + ")"; //
        return coordinates;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        if (!zipcode.equals(address.zipcode)) return false;
        if (!getFullAddress().equals(address.getFullAddress())) return false;
        return getCoordinates().equals(address.getCoordinates());
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + zipcode.hashCode();
        result = 31 * result + getFullAddress().hashCode();
        result = 31 * result + getCoordinates().hashCode();
        return result;
    }
}