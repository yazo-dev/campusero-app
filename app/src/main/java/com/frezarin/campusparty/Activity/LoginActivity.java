package com.frezarin.campusparty.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.Event;
import com.frezarin.campusparty.API.model.EventInfo;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.Adapter.SpinnerAdapter;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private String client_id = "NAleBVoHoEtbSW00H6SftUlLirIvvoXIyg6R28DO";
    private String secret_key = "QrmUOa8Im2qwBqunxBKRYynR7DbrUMrxrYK44MqSlpMmC3fF8Q6Y8e00Gxf1474sUDURXJIA61oCAPknMu6q88nyNtywzaK3YA6cQc2LgWVKaZl54Hk3dIRfPCcgasR6";

    private String response_type = "code";
    private String grant_type = "authorization_code";
    private String redirect_uri = "http://yazo.com/callback";

    private EventInfo mEventInfo;
    private SpinnerAdapter spinnerAdapter;
    private Spinner spinner;

    private Button bt_login;
    private Button bt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = findViewById(R.id.bt_login);
        bt_signup = findViewById(R.id.bt_signup);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginOAuth();
            }
        });

        LoadEvents();
    }

    private void LoadEvents() {
        spinner = (Spinner) findViewById(R.id.spinner_location);

        Retrofit retrofit = Service.retrofit();
        CampuseClient api = retrofit.create(CampuseClient.class);
        Call<EventInfo> call = api.getEvents();
        call.enqueue(new Callback<EventInfo>() {
            @Override
            public void onResponse(Call<EventInfo> call, Response<EventInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mEventInfo = response.body();
                    spinnerAdapter = new SpinnerAdapter(mEventInfo.list, LoginActivity.this);
                    spinner.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onFailure(Call<EventInfo> call, Throwable t) {

            }
        });
    }

    private void openLoginOAuth() {
        String url = "https://sandboxaccounts.campuse.ro/o/authorize/?response_type=" + response_type + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirect_uri)) {
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            String code = uri.getQueryParameter("code");
            //Toast.makeText(this, "Hey!", Toast.LENGTH_SHORT);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://sandboxaccounts.campuse.ro/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            CampuseClient api = retrofit.create(CampuseClient.class);

            Call<AccessToken> call = api.getAccessToken(client_id, secret_key, grant_type, code, redirect_uri);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        //Save Token
                        Service.saveToken(getApplicationContext(), response.body());

                        //Open Profile
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("token", response.body());
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to authenticate", Toast.LENGTH_LONG).show();
                    }

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "No!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
