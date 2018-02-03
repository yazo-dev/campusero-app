package com.frezarin.campusparty.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;
import com.github.chrisbanes.photoview.PhotoView;

public class ImagePostActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String EXTRA_IMAGED = "DetailActivity:imageD";

    private PhotoView mImageView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String imageUrl = "";

        mImageView = (PhotoView) findViewById(R.id.img_publicacao);
        mImageView.setMaximumScale(2);

        findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Carregar imagem de postagem na timeline
        Bundle extras = getIntent().getExtras();

        //Carregar qualquer imagem a partir de uma url
        if (extras != null && extras.containsKey(EXTRA_IMAGE)) {
            imageUrl = getIntent().getStringExtra(EXTRA_IMAGE);
            ViewCompat.setTransitionName(mImageView, EXTRA_IMAGE);
            Service.LoadImageByUrl(this, imageUrl, mImageView);
        }
        else if (extras != null && extras.containsKey(EXTRA_IMAGED)) {
            int id = getIntent().getIntExtra(EXTRA_IMAGED,0);
            if(id != 0){
                mImageView.setImageDrawable(getResources().getDrawable(id));
            }
            else
            {
                finish();
            }

        }else {
            finish();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_image_post;
    }

    public static void launch(BaseActivity activity, View transitionView, String imageUrl) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, ImagePostActivity.class);
        intent.putExtra(EXTRA_IMAGE, imageUrl);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void launch(BaseActivity activity, int drawableImage) {
        Intent intent = new Intent(activity, ImagePostActivity.class);
        intent.putExtra(EXTRA_IMAGED, drawableImage);
        activity.startActivity(intent);
    }
}
