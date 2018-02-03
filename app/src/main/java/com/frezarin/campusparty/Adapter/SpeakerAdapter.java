package com.frezarin.campusparty.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Profile;
import com.frezarin.campusparty.API.model.Speaker;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gustavo on 22/02/2017.
 */

public class SpeakerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 1;

    private Context mContext;

    public List<Speaker> mList;
    public LayoutInflater mInflater;

    private AdapterOnClickListener mAdapterOnClickListener;

    public SpeakerAdapter(Context c, List<Speaker> lista) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = lista;
        mContext = c;
    }

    public void setmAdapterOnClickListener(AdapterOnClickListener mAdapterOnClickListener) {
        this.mAdapterOnClickListener = mAdapterOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = mInflater.inflate(R.layout.item_usuario, parent, false);
        UsuarioViewHolder usuarioViewHolder = new UsuarioViewHolder(view);
        return usuarioViewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Speaker user = mList.get(position);

        if (holder instanceof UsuarioViewHolder) {
            UsuarioViewHolder userHolder = (UsuarioViewHolder) holder;
            String cidade = mContext.getString(R.string.app_name);


            userHolder.tv_nome.setText(user.first_name + " " + user.last_name);
            userHolder.tv_cidade.setText(user.title);

            //Load thumbnail
            Picasso.with(mContext)
                    .load(user.avatar_url)
                    .placeholder(R.drawable.noprofile)
                    .error(R.drawable.noprofile)
                    .into(userHolder.img_perfil);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }


    public Speaker getSpeaker(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_nome;
        public TextView tv_cidade;
        public CircleImageView img_perfil;

        public UsuarioViewHolder(View itemView) {
            super(itemView);

            tv_nome = (TextView) itemView.findViewById(R.id.tv_nome);
            tv_cidade = (TextView) itemView.findViewById(R.id.tv_cidade);
            img_perfil = (CircleImageView) itemView.findViewById(R.id.img_perfil);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mAdapterOnClickListener != null)
                mAdapterOnClickListener.onAdapterOnClickListener(view, getPosition());
        }
    }
}