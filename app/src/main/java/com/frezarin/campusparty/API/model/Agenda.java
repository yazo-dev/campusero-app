package com.frezarin.campusparty.API.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by macbook on 02/02/2018.
 */

public class Agenda implements Serializable {

    @SerializedName("main_hashtag")
    public String main_hashtag;

    @SerializedName("slug")
    public String slug;

    @SerializedName("title")
    public String title;

    @SerializedName("stadium_name")
    public String stadium_name;

    @SerializedName("description")
    public String description;

    @SerializedName("start_date")
    public String start_date;

    @SerializedName("end_date")
    public String end_date;

    @SerializedName("speakers")
    public String speakers;

    @SerializedName("background_image")
    public String background_image;

    @SerializedName("url")
    public String url;
}
