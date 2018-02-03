package com.frezarin.campusparty.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Agenda;
import com.frezarin.campusparty.Interface.AdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 22/02/2017.
 */

public class AgendaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_LOADING = 2;

    public boolean isLoading = true;

    private Context mContext;

    public List<Agenda> mList;
    public LayoutInflater mInflater;

    private AdapterOnClickListener mAdapterOnClickListener;

    public AgendaAdapter(Context c, List<Agenda> lista) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = lista;

        //Verificar se a lista esta vazia
        if (mList == null) {
            mList = new ArrayList<>();
            isLoading = false;
        } else {
            mList.add(new Agenda());
        }

        mContext = c;
    }

    public void setmAdapterOnClickListener(AdapterOnClickListener mAdapterOnClickListener) {
        this.mAdapterOnClickListener = mAdapterOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_ITEM:
                view = mInflater.inflate(R.layout.item_agenda, parent, false);
                UsuarioViewHolder usuarioViewHolder = new UsuarioViewHolder(view);
                return usuarioViewHolder;

            case TYPE_LOADING:
                view = mInflater.inflate(R.layout.item_loading, parent, false);
                LoadingViewHolder loadingViewHolder = new LoadingViewHolder(view);
                return loadingViewHolder;

            default:
                return null;

        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Agenda agenda = mList.get(position);

        if (holder instanceof UsuarioViewHolder) {
            UsuarioViewHolder userHolder = (UsuarioViewHolder) holder;


            userHolder.tv_title.setText(agenda.title);
            userHolder.tv_descricao.setText(agenda.stadium_name);
        }
        //Holder loading, verificar se ainda existe novas postagens
        //Se não, desabilita o loading
        else if (holder instanceof LoadingViewHolder) {
            if (isLoading)
                holder.itemView.setVisibility(View.VISIBLE);
            else {
                holder.itemView.setVisibility(View.GONE);
                //notifyItemRemoved(position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionBottom(position))
            return TYPE_LOADING;

        return TYPE_ITEM;
    }

    private boolean isPositionBottom(int position) {
        return position == (mList.size() - 1);
    }

    public Agenda getUsuario(int position) {
        return mList.get(position);
    }

    public void addListItem(Agenda item, int position) {
        mList.add(item);
        notifyItemInserted(position);
    }

    public void addList(List<Agenda> list) {
        int position = mList.size() - 1;
        mList.addAll(position, list);
        notifyDataSetChanged();
    }

    public void clearList(){
        mList = new ArrayList<>();
        mList.add(new Agenda());
        isLoading = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_title;
        public TextView tv_descricao;

        public UsuarioViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_descricao = (TextView) itemView.findViewById(R.id.tv_descricao);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mAdapterOnClickListener != null)
                mAdapterOnClickListener.onAdapterOnClickListener(view, getPosition());
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);

        }
    }
}