package com.frezarin.campusparty.API.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 02/02/2018.
 */

public class Speaker implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("slug")
    public String slug;

    @SerializedName("title")
    public String title;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("bio")
    public String bio;

    @SerializedName("site")
    public String site;

    @SerializedName("github")
    public String github;

    @SerializedName("flickr")
    public String flickr;

    @SerializedName("instagram")
    public String instagram;

    @SerializedName("twitter")
    public String twitter;

    @SerializedName("facebook")
    public String facebook;

    @SerializedName("linkedin")
    public String linkedin;

    @SerializedName("whatsapp")
    public String whatsapp;

    @SerializedName("photo_url")
    public String avatar_url;
}
