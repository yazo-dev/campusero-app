package com.frezarin.campusparty.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by macbook on 02/02/2018.
 */

public class Service {
    private static String BASE_URL = "https://sandboxapi.campuse.ro/";

    private static AccessToken UserAccessToken;
    public static Profile UserProfile;

    public static AccessToken getAccessToken(Context context){
        if(UserAccessToken != null){
            return UserAccessToken;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", MODE_PRIVATE);
        String json = sharedPreferences.getString("Token", null);
        Gson gson = new Gson();
        UserAccessToken = gson.fromJson(json, AccessToken.class);
        return UserAccessToken;
    }

    public static void saveToken(Context context, AccessToken token){
        //Instanciar a variavel TokenAuthentication
        UserAccessToken = token;

        //Salvar dentro do celular o objeto authentication
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(token);
        editor.putString("Token", json);
        editor.commit();
    }

    public static void Logout(Context context) {
        //Delete authentication saved
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", null);
        editor.commit();
    }

    public static Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static void LoadImageByUrl(Context c, String URL, ImageView view){
        Picasso.with(c)
                .load(URL)
                .placeholder(R.drawable.noprofile)
                .error(R.drawable.noprofile)
                .into(view);
    }

    private static OkHttpClient getRequestHeader() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build();

        return okHttpClient;
    }
}
