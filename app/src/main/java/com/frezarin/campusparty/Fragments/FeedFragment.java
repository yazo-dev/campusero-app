package com.frezarin.campusparty.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.frezarin.campusparty.API.model.Publicacao;
import com.frezarin.campusparty.Activity.ImagePostActivity;
import com.frezarin.campusparty.Activity.MainActivity;
import com.frezarin.campusparty.Activity.ProfileActivity;
import com.frezarin.campusparty.Adapter.FeedAdapter;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.Interface.FeedAdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends MainFragment implements FeedAdapterOnClickListener {

    private static final int SELECT_PICTURE = 55;
    private static final int COMPARTILHAR_POST = 56;

    private SwipeRefreshLayout swipeContainer;

    private View mView;
    private RecyclerView mRecycler;
    private FeedAdapter mAdapter;
    private List<Publicacao> mList;
    private boolean HavePost;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCustomToolbar("Timeline");
        setCustomBackground();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_feed, container, false);

        HavePost = true;
        mList = Publicacao.loadFakePosts();

        //mUsuario = Authentication.getAuthenticated(getActivity());
        mRecycler = (RecyclerView) mView.findViewById(R.id.rv_post);

        if (!HavePost && (mList == null || mList.size() <= 0)) {
            mRecycler.setVisibility(View.GONE);
            mView.findViewById(R.id.tab_msg).setVisibility(View.VISIBLE);
        }

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);


        mAdapter = new FeedAdapter(getActivity(), mList, HavePost);
        mAdapter.setmAdapterOnClickListener(this);
        mRecycler.setAdapter(mAdapter);


        return mView;
    }

    @Override
    public void onAdapterOnClickListener(View view, final int position, int viewType) {
        int itemId = view.getId();
        switch (viewType) {
            //Header type
            case FeedAdapter.TYPE_HEADER:
                switch (itemId) {
                    case R.id.bt_publicar:
                        Toast.makeText(getContext(), "This app is still only a mockup", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.bt_addFoto:
                        CropImage.activity()
                                .setRequestedSize(1000, 1000)
                                .setMinCropResultSize(1000, 1000)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(getContext(), this);
                        break;

                    case R.id.bt_removeImagem:
                        //mAdapter.postTexto = ((EditText)view.findViewById(R.id.tb_texto)).getText().toString();
                        mAdapter.postImageUri = null;
                        mAdapter.notifyItemChanged(0);
                }
                break;

            //Post type
            case FeedAdapter.TYPE_ITEM:
                Publicacao publicacao = mList.get(position);
                switch (itemId) {
                    case R.id.icon_curtida:

                    case R.id.bt_curtir:
                        //Verifica se já foi curtido
                        //Se falso, deve curtir a foto
                        if (!publicacao.Curtiu) {
                            //Adiciona a curtida na view
                            publicacao.Curtiu = true;
                            publicacao.Curtidas++;
                            mAdapter.notifyItemChanged(position);
                        }
                        //Descurtir a foto
                        else {
                            //Remove a curtida na view
                            publicacao.Curtiu = false;
                            publicacao.Curtidas--;
                            mAdapter.notifyItemChanged(position);
                        }

                        break;
                    case R.id.tv_comentarios:
                    case R.id.tv_descricao:
//                    case R.id.bt_comentar:
//                        Intent i = new Intent(getActivity(), PostagemActivity.class);
//                        i.putExtra("publicacao", publicacao);
//                        startActivity(i);
//                        break;

//                    case R.id.iv_opcoes:
//                        showPopup(view, position);
//                        break;

                    case R.id.img_publicacao:
                        ImagePostActivity.launch((MainActivity) getActivity(), view, publicacao.Photo);
                        break;
                    default:
                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                        intent.putExtra("profile", publicacao.Usuario);
                        startActivity(intent);
                        break;
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPARTILHAR_POST)
            HiddenProgressBar();

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mAdapter.postImageUri = result.getUri();
                mAdapter.notifyItemChanged(0);
            }
        }
    }
}
