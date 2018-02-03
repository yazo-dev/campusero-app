package com.frezarin.campusparty.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.Activity.ProfileActivity;
import com.frezarin.campusparty.Adapter.UsuarioAdapter;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import java.util.List;


public class RankingFragment extends MainFragment implements AdapterOnClickListener {

    private View mView;
    private List<Profile> mList;

    private RecyclerView mRecycler;
    private UsuarioAdapter mAdapter;

    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        setCustomBackground();
        setCustomToolbar("Ranking");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_ranking, container, false);
        mList = Profile.loadFakeUsers();
        mList.add(new Profile(Service.UserProfile.username, "", Service.UserProfile.avatar_url));

        ImageView img_rank1 = mView.findViewById(R.id.img_rank1);
        ImageView img_rank2 = mView.findViewById(R.id.img_rank2);
        ImageView img_rank3 = mView.findViewById(R.id.img_rank3);

        Service.LoadImageByUrl(getActivity(), mList.get(0).avatar_url, img_rank1);
        Service.LoadImageByUrl(getActivity(), mList.get(1).avatar_url, img_rank2);
        Service.LoadImageByUrl(getActivity(), mList.get(2).avatar_url, img_rank3);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_users);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);

        mAdapter = new UsuarioAdapter(getActivity(), mList, true);
        mAdapter.setmAdapterOnClickListener(RankingFragment.this);
        mRecycler.setAdapter(mAdapter);

        return mView;
    }

    @Override
    public void onAdapterOnClickListener(View view, int position) {
        Profile usuario = mAdapter.getUsuario(position);

        Intent i = new Intent(getActivity(), ProfileActivity.class);
        i.putExtra("profile", usuario);
        startActivity(i);
    }
}
