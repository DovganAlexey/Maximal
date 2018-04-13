package com.enzo.testaufgabe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by enzo on 12.04.18.
 */

public class Message implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}