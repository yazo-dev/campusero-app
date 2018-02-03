package com.frezarin.campusparty.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frezarin.campusparty.API.model.AccessToken;
import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.service.CampuseClient;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


public class FavoritesFragment extends MainFragment {

    private View mView;
    private EditText mNotes;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCustomToolbar("Notes");
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
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        mNotes = mView.findViewById(R.id.et_notes);

        loadNotes();

        mView.findViewById(R.id.bt_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotes(mNotes.getText().toString());
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        return mView;
    }

    private void saveNotes(String notas){
        //Instanciar a variavel TokenAuthentication

        //Salvar dentro do celular o objeto authentication
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Notes", notas);
        editor.commit();
    }

    private void loadNotes(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String note = sharedPreferences.getString("Notes", null);
        mNotes.setText(note);

        mNotes.setSelection(mNotes.getText().length());
    }
}
