package com.frezarin.campusparty.API.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by macbook on 02/02/2018.
 */

public class Event implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("slug")
    public String slug;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("main_hashtag")
    public String main_hashtag;
}
