package com.frezarin.campusparty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.frezarin.campusparty.R;
//import com.frezarin.campusparty.VideoPlayback.VideoPlayback;

public class InfoARActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ar);


        //BOTAO DE FECHAR
        findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Retira a mensagem e inicia a camera
        findViewById(R.id.bt_confirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(InfoARActivity.this, VideoPlayback.class);
//                startActivity(i);
//                finish();
            }
        });
    }
}
