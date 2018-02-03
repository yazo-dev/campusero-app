package com.frezarin.campusparty.Utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by macbook on 01/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RA implements Serializable {
    @SerializedName("code")
    public String Codigo;

    @SerializedName("url")
    public String VideoUrl;
}
