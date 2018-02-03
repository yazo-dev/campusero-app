package com.frezarin.campusparty.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.Activity.ImagePostActivity;
import com.frezarin.campusparty.Activity.MainActivity;
import com.frezarin.campusparty.Activity.ProfileActivity;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends MainFragment {

    private View mView;
    private CircleImageView img_profile;
    private TextView tv_name;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCustomToolbar("");
        setCustomBackground();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        tv_name = mView.findViewById(R.id.tv_name);
        img_profile = mView.findViewById(R.id.img_perfil);

        mView.findViewById(R.id.bt_timeline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(FeedFragment.newInstance());
            }
        });

        mView.findViewById(R.id.bt_people).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(ParticipantesFragment.newInstance());
            }
        });

        mView.findViewById(R.id.bt_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePostActivity.launch((MainActivity) getActivity(), R.drawable.map_events);
            }
        });

        loadProfile();

        return mView;
    }

    private void loadProfile(){
        ShowProgressBar();

        Retrofit retrofit = Service.retrofit();
        CampuseClient api = retrofit.create(CampuseClient.class);

        Call<Profile> call = api.getProfile(Service.getAccessToken(getContext()).getTokenType() + " " +Service.getAccessToken(getContext()).getAccessToken());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){
                    Service.UserProfile = response.body();

                    if(Service.UserProfile.username != null && !Service.UserProfile.username.isEmpty())
                        tv_name.setText("Welcome, " + Service.UserProfile.username);
                    Service.LoadImageByUrl(getActivity(), Service.UserProfile.avatar_url, img_profile);
                }

                HiddenProgressBar();
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), "Verifique sua internet.", Toast.LENGTH_LONG).show();
                HiddenProgressBar();
            }
        });
    }

}
