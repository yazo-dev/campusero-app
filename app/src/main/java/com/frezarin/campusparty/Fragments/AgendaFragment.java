package com.frezarin.campusparty.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.Agenda;
import com.frezarin.campusparty.API.model.AgendaInfo;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.Activity.AgendaActivity;
import com.frezarin.campusparty.Adapter.AgendaAdapter;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends MainFragment implements AdapterOnClickListener {

    private View mView;
    private AgendaInfo mInfo;

    private RecyclerView mRecycler;
    private AgendaAdapter mAdapter;

    private boolean isLoading = false;

    private RecyclerView.OnScrollListener scrollListener;

    public AgendaFragment() {
        // Required empty public constructor
    }

    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCustomToolbar("Agenda");
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
        mView = inflater.inflate(R.layout.fragment_agenda, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager ll = (LinearLayoutManager) mRecycler.getLayoutManager();

                if (!isLoading && mInfo.list.size() == (ll.findLastVisibleItemPosition() + 1)) {

                    //Carrega mais usuarios
                    NextPage();
                }
            }
        };


        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_users);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);

        mRecycler.setOnScrollListener(scrollListener);
        mAdapter = new AgendaAdapter(getActivity(), new ArrayList<Agenda>());
        mAdapter.setmAdapterOnClickListener(AgendaFragment.this);
        mRecycler.setAdapter(mAdapter);

        NextPage();

        return mView;
    }

    //FUNÇÃO PARA ATUALZIAR A TIME LINE
    private void NextPage() {
        if(mInfo != null && (mInfo.next == null || mInfo.next.isEmpty()))
            return;

        CampuseClient api;
        Retrofit retrofit = Service.retrofit();
        api = retrofit.create(CampuseClient.class);

        isLoading = true;

        Call<AgendaInfo> call;
        if(mInfo == null){
            call = api.getAgenda(Service.getAccessToken(getContext()).getEvent_slug());
        }
        else
        {
            call = api.getAgendUrl(mInfo.next);
        }

        call.enqueue(new Callback<AgendaInfo>() {
            @Override
            public void onResponse(Call<AgendaInfo> call, Response<AgendaInfo> response) {
                if (response.isSuccessful() && validarIsVisivel()) {

                    //Remove o loading
                    if (response.body() == null)
                        mAdapter.isLoading = false;
                    if(mInfo == null){
                        mInfo = response.body();
                        mAdapter.addList(response.body().list);
                    }
                    else if(response.body().list != null) {
                        mAdapter.addList(response.body().list);
                        mInfo.list.addAll(response.body().list);
                        mInfo.next = response.body().next;
                    }


                }

                isLoading = false;
            }

            @Override
            public void onFailure(Call<AgendaInfo> call, Throwable t) {
                Toast.makeText(getContext(), "Tente novamente", Toast.LENGTH_LONG).show();
                isLoading = false;
            }
        });
    }

    @Override
    public void onAdapterOnClickListener(View view, int position) {
        Agenda agenda = mAdapter.getUsuario(position);

        Intent i = new Intent(getActivity(), AgendaActivity.class);
        i.putExtra("agenda", agenda);
        startActivity(i);
    }

}
