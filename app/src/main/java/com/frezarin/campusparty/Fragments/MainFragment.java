package com.frezarin.campusparty.Fragments;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.frezarin.campusparty.Activity.MainActivity;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.ToolbarHelper;


public abstract class MainFragment extends Fragment {

    public NavigationView mNavigation;
    public ImageView mMainLayout;
    public Toolbar mToolbar;


    public void setCustomToolbar(String title) {
        getActivity().setTitle(title);
    }

    public void setCustomToolbar(int stringRes){
        mToolbar.setTitle(getContext().getString(stringRes));
    }

    public void replaceFragment(MainFragment fragment){
        MainActivity activity = (MainActivity) getActivity();
        activity.replaceFragment(fragment);
    }

    public void setCustomBackground() {
        //setVisibleSearchView(false);
        ToolbarHelper.colorizeToolbar(mToolbar, R.color.branco, getActivity());
        mMainLayout.setImageDrawable(getResources().getDrawable(R.drawable.campus_bg));
    }

    public boolean validarIsVisivel(){
        return isVisible();
    }

    public void ShowProgressBar(){
        if(getActivity() != null)
            getActivity().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    public void HiddenProgressBar(){
        if(getActivity() != null)
            getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mNavigation = (NavigationView) getActivity().findViewById(R.id.nav_view);
        mMainLayout = (ImageView) getActivity().findViewById(R.id.main_background);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Remove o progress bar
        getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

}
