package com.frezarin.campusparty.API.service;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.AgendaInfo;
import com.frezarin.campusparty.API.model.EventInfo;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.model.Speaker;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by macbook on 02/02/2018.
 */

public interface CampuseClient {

    @Headers("Accept: aplication/json")
    @POST("/o/token/")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String client_id,
            @Field("secret_key") String secret_key,
            @Field("grant_type") String grant_type,
            @Field("code") String code,
            @Field("redirect_uri") String redirect_uri
    );

    //https://sandboxapi.campuse.ro
    @GET("/user/myprofile/")
    Call<Profile> getProfile(@Header("Authorization") String token);

    @GET("/event/list/")
    Call<EventInfo> getEvents();

    @GET("/speaker/list/")
    Call<List<Speaker>> getSpeakers();

    @GET("/agenda/list/{event_slug}/")
    Call<AgendaInfo> getAgenda(@Path("event_slug") String event_slug);

    @GET
    Call<AgendaInfo> getAgendUrl(@Url String url);
}
