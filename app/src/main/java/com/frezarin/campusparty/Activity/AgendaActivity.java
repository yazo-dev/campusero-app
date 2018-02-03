package com.frezarin.campusparty.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Agenda;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgendaActivity extends BaseActivity {

    private CircleImageView img_profile;
    private TextView tv_name;
    private TextView tv_about;
    private TextView tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        tv_name = findViewById(R.id.tv_name);
        tv_about = findViewById(R.id.tv_about);
        tv_location = findViewById(R.id.tv_location);
        img_profile = findViewById(R.id.img_perfil);

        loadAgenda();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_agenda;
    }

    private void loadAgenda() {

        if (getIntent().getSerializableExtra("agenda") != null) {
            final Agenda agenda = (Agenda) getIntent().getSerializableExtra("agenda");
            tv_name.setText(agenda.title);
            tv_location.setText(agenda.stadium_name);
            tv_about.setText(Html.fromHtml(agenda.description));
            //Service.LoadImageByUrl(AgendaActivity.this, agenda.background_image, img_profile);

//            img_profile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ImagePostActivity.launch(AgendaActivity.this, img_profile, agenda.background_image);
//                }
//            });
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
