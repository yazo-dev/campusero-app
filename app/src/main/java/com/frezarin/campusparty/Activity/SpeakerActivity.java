package com.frezarin.campusparty.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.model.Speaker;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpeakerActivity extends BaseActivity {

    private CircleImageView img_profile;
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        tv_name = findViewById(R.id.tv_name);
        tv_title = findViewById(R.id.tv_title);
        tv_descricao = findViewById(R.id.tv_descricao);
        img_profile = findViewById(R.id.img_perfil);

        loadProfile();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_speaker;
    }

    private void loadProfile() {

        if (getIntent().getSerializableExtra("speaker") != null) {
            Speaker speaker = (Speaker) getIntent().getSerializableExtra("speaker");
            tv_name.setText(speaker.first_name + " " + speaker.last_name);
            tv_title.setText(speaker.title);
            tv_descricao.setText(Html.fromHtml(speaker.bio));
            Service.LoadImageByUrl(SpeakerActivity.this, speaker.avatar_url, img_profile);
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
