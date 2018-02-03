package com.frezarin.campusparty.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.Fragments.AgendaFragment;
import com.frezarin.campusparty.Fragments.FavoritesFragment;
import com.frezarin.campusparty.Fragments.FeedFragment;
import com.frezarin.campusparty.Fragments.ParticipantesFragment;
import com.frezarin.campusparty.Fragments.ProfileFragment;
import com.frezarin.campusparty.Fragments.RankingFragment;
import com.frezarin.campusparty.Fragments.SpeakersFragment;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigation;

    private ImageView mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainLayout = (ImageView) findViewById(R.id.main_background);

        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //Inserir os conteudos do header
        setContentsHeader();

        mNavigation.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //Insere cor branca no Hamburguer
        //Por algum motivo com isso inserido o toggle consegue mudar de cor
        //utilizando o metodo colorizeToolbar() do ToolbarHelper
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.branco));
        } else {
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.branco));
        }

//        //Load usuario autenticado
//        Authentication.getAuthenticated(getBaseContext());

        //Inicia o primeiro fragment
        replaceFragment(ProfileFragment.newInstance());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    public void replaceFragment(Fragment fragment) {
        //Atualizar os conteudos do header
        setContentsHeader();

        //Replace no fragment principal
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.layout_fragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_timeline:
                replaceFragment(FeedFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_people:
                replaceFragment(ParticipantesFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_favorites:
                replaceFragment(FavoritesFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_map:
                ImagePostActivity.launch((MainActivity) this, R.drawable.map_events);
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_agenda:
                replaceFragment(AgendaFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_speakers:
                replaceFragment(SpeakersFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_ranking:
                replaceFragment(RankingFragment.newInstance());
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_qr:
                Intent qr = new Intent(this, ScannerQRActivity.class);
                startActivity(qr);
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_ra:
                Intent i = new Intent(this, InfoARActivity.class);
                startActivity(i);
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.item_logout:
                Service.Logout(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //INSERE A IMAGEM E O NOME DO USUÁRIO NO HEADER DO NAVIGATION DRAWER
    public void setContentsHeader() {
        if (mNavigation != null) {

            AccessToken mAccessToken = Service.getAccessToken(getApplicationContext());

            if (mAccessToken == null)
                return;

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://sandboxapi.campuse.ro/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            CampuseClient api = retrofit.create(CampuseClient.class);

            Call<Profile> call = api.getProfile(mAccessToken.getTokenType() + " " + mAccessToken.getAccessToken());
            call.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if (response.isSuccessful()) {
                        Service.UserProfile = response.body();

                        if (mNavigation.getHeaderView(0) != null) {
                            //Busca o header no navigation view
                            View header = mNavigation.getHeaderView(0);

                            //Buscar views
                            ImageView img_perfil = (ImageView) header.findViewById(R.id.img_perfil);
                            TextView tv_nome = (TextView) header.findViewById(R.id.tv_nome);

                            img_perfil.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    replaceFragment(ProfileFragment.newInstance());
                                    mDrawerLayout.closeDrawers();
                                }
                            });

                            //Inserir dados
                            Service.LoadImageByUrl(MainActivity.this, Service.UserProfile.avatar_url, img_perfil);
                            tv_nome.setText(Service.UserProfile.first_name + " " + Service.UserProfile.username);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });

        }
    }

}
