package com.frezarin.campusparty.API.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by macbook on 02/02/2018.
 */

public class AgendaInfo implements Serializable {

    @SerializedName("count")
    public String count;

    @SerializedName("next")
    public String next;

    @SerializedName("previous")
    public String previous;

    @SerializedName("results")
    public List<Agenda> list;
}
