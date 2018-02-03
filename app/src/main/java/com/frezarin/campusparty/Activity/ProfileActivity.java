package com.frezarin.campusparty.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends BaseActivity {

    private CircleImageView img_profile;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        tv_name = findViewById(R.id.tv_name);
        img_profile = findViewById(R.id.img_perfil);

        loadProfile();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }

    private void loadProfile() {

        if (getIntent().getSerializableExtra("profile") != null) {
            final Profile mUser = (Profile) getIntent().getSerializableExtra("profile");
            tv_name.setText(mUser.first_name + " " + mUser.last_name);
            Service.LoadImageByUrl(ProfileActivity.this, mUser.avatar_url, img_profile);

            img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePostActivity.launch(ProfileActivity.this, img_profile, mUser.avatar_url);
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
