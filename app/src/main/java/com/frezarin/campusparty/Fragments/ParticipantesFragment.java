package com.frezarin.campusparty.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.Activity.ProfileActivity;
import com.frezarin.campusparty.Adapter.UsuarioAdapter;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;

import java.util.List;


public class ParticipantesFragment extends MainFragment implements AdapterOnClickListener {

    private View mView;
    private List<Profile> mList;

    private RecyclerView mRecycler;
    private UsuarioAdapter mAdapter;

    public ParticipantesFragment() {
        // Required empty public constructor
    }

    public static ParticipantesFragment newInstance() {
        ParticipantesFragment fragment = new ParticipantesFragment();
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        setCustomBackground();
        setCustomToolbar("People");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_usuarios, container, false);
        mList = Profile.loadFakeUsers();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_users);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);

        mAdapter = new UsuarioAdapter(getActivity(), mList, false);
        mAdapter.setmAdapterOnClickListener(ParticipantesFragment.this);
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
