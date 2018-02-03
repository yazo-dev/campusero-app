package com.frezarin.campusparty.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.frezarin.campusparty.R;

/**
 * Created by macbook on 02/02/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarTitle(String title) {
        mToolbar.setTitle(title);
    }

    protected void setActionBarTitle(int stringRes) {
        setTitle(getString(stringRes));
    }

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }
}