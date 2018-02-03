package com.frezarin.campusparty.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.model.Speaker;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.Activity.ProfileActivity;
import com.frezarin.campusparty.Activity.SpeakerActivity;
import com.frezarin.campusparty.Adapter.SpeakerAdapter;
import com.frezarin.campusparty.Adapter.UsuarioAdapter;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SpeakersFragment extends MainFragment implements AdapterOnClickListener {

    private View mView;
    private List<Speaker> mList;

    private RecyclerView mRecycler;
    private SpeakerAdapter mAdapter;

    public SpeakersFragment() {
        // Required empty public constructor
    }

    public static SpeakersFragment newInstance() {
        SpeakersFragment fragment = new SpeakersFragment();
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        setCustomBackground();
        setCustomToolbar("Speakers");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_usuarios, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        ShowProgressBar();

        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_users);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);

        Retrofit retrofit = Service.retrofit();
        CampuseClient api = retrofit.create(CampuseClient.class);

        Call<List<Speaker>> call = api.getSpeakers();
        call.enqueue(new Callback<List<Speaker>>() {
            @Override
            public void onResponse(Call<List<Speaker>> call, Response<List<Speaker>> response) {
                if(response.isSuccessful()){
                    mList = response.body();

                    mAdapter = new SpeakerAdapter(getActivity(), mList);
                    mAdapter.setmAdapterOnClickListener(SpeakersFragment.this);
                    mRecycler.setAdapter(mAdapter);
                }

                HiddenProgressBar();
            }

            @Override
            public void onFailure(Call<List<Speaker>> call, Throwable t) {
                Toast.makeText(getActivity(), "Verifique sua internet.", Toast.LENGTH_LONG).show();
                HiddenProgressBar();
            }
        });


        return mView;
    }

    @Override
    public void onAdapterOnClickListener(View view, int position) {
        Speaker speaker = mAdapter.getSpeaker(position);

        Intent i = new Intent(getActivity(), SpeakerActivity.class);
        i.putExtra("speaker", speaker);
        startActivity(i);
    }
}
