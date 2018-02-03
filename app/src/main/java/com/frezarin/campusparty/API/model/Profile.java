package com.frezarin.campusparty.API.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 02/02/2018.
 */

public class Profile implements Serializable {

//    {"nick": null, "": "", "": "", "occupation": "", "interest_tags": [], "specialities": [], "username": "_borgss",
//            "primary_role": "nothing", "email": "gustavo@frezarin.com", "secondary_email": null, "about": "",
//            "date_of_birth": null, "gender": null, "gender_name": null, "site": null, "github": "", "flickr": null,
//            "instagram": null, "twitter": null, "facebook": null, "linkedin": null, "googleplus": null, "whatsapp": null,
//            "msn": "", "phone_number": null, "mob_phone_number": null, "skype": "", "city": 1748,
//            "city_name": "Londrina, Paran\u00e1, Brazil", "zipcode": null, "street": null, "street_number": null,
//            "address_other": null, "language": [], "main_language": null, "avatar_url": "/media/default/profile.png",
//            "raw_avatar_url": "/media/default/profile.png"}


    public Profile(){

    }

    public Profile(String first, String last, String image){
        first_name = first;
        last_name = last;
        username = first + " " + last;
        city_name = "Londrina - PR";
        avatar_url = image;
    }

    @SerializedName("nick")
    public String nick;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("occupation")
    public String occupation;

    @SerializedName("username")
    public String username;

    @SerializedName("primary_role")
    public String primary_role;

    @SerializedName("email")
    public String email;

    @SerializedName("about")
    public String about;

    @SerializedName("date_of_birth")
    public String date_of_birth;

    @SerializedName("gender")
    public String gender;

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

    @SerializedName("mob_phone_number")
    public String mob_phone_number;

    @SerializedName("phone_number")
    public String phone_number;

    @SerializedName("city_name")
    public String city_name;

    @SerializedName("main_language")
    public String main_language;

    @SerializedName("avatar_url")
    public String avatar_url;

    @SerializedName("raw_avatar_url")
    public String raw_avatar_url;

    public static List<Profile> loadFakeUsers(){
        List<Profile> list = new ArrayList<>();
        list.add(new Profile("Eduardo", "Frezarin", "http://frezarin.com/yazo/integrantes/eduardo.jpg"));
        list.add(new Profile("Giovana", "Brianti", "http://frezarin.com/yazo/integrantes/giovana.jpg"));
        list.add(new Profile("Guilherme", "Silva", "http://frezarin.com/yazo/integrantes/guilherme.jpg"));
        list.add(new Profile("Gustavo", "Cavalcante", "http://frezarin.com/yazo/integrantes/gustavo.jpg"));
        list.add(new Profile("Jonata", "William", "http://frezarin.com/yazo/integrantes/jonata.jpg"));
        return list;
    }
}
