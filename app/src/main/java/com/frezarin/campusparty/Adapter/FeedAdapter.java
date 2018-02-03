package com.frezarin.campusparty.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Publicacao;
import com.frezarin.campusparty.Interface.FeedAdapterOnClickListener;
import com.frezarin.campusparty.R;
import com.frezarin.campusparty.Utils.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gustavo on 11/02/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_LOADING = 2;

    public boolean isLoading = false;

    public Uri postImageUri;
    public String postTexto;

    private Context mContext;
    private List<Publicacao> mList;
    private LayoutInflater mInflater;
    private FeedAdapterOnClickListener mAdapterOnClickListener;
    private boolean NovaPostagem;

    public void setmAdapterOnClickListener(FeedAdapterOnClickListener mAdapterOnClickListener) {
        this.mAdapterOnClickListener = mAdapterOnClickListener;
    }

    public FeedAdapter(Context c, List<Publicacao> lista, boolean haveNewPost) {
        mList = lista;

        //Verificar se a lista esta vazia
        if (mList == null) {
            mList = new ArrayList<>();
            isLoading = false;
        }


        NovaPostagem = haveNewPost;

        //Adicionar o index 0 para o item de postagem
        if (NovaPostagem)
            mList.add(0, new Publicacao());

        //Adiciona o objeto loading no fim da lista
        mList.add(new Publicacao());

        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Validar o tipo de item
        View view;

        switch (viewType) {
            case TYPE_HEADER:
                view = mInflater.inflate(R.layout.item_postagem, parent, false);
                PostagemViewHolder postagemViewHolder = new PostagemViewHolder(view);
                return postagemViewHolder;

            case TYPE_ITEM:
                view = mInflater.inflate(R.layout.item_feed, parent, false);
                FeedViewHolder feedViewHolder = new FeedViewHolder(view);
                return feedViewHolder;

            case TYPE_LOADING:
                view = mInflater.inflate(R.layout.item_loading, parent, false);
                LoadingViewHolder loadingViewHolder = new LoadingViewHolder(view);
                return loadingViewHolder;

            default:
                return null;
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Publicacao publicacao = mList.get(position);

        //Validar o tipo de holder
        //Esse holder é de uma postagem
        if (viewHolder instanceof FeedViewHolder) {
            //dar cast no holder
            final FeedViewHolder holder = (FeedViewHolder) viewHolder;

            //VERIFICA SE EXISTE MENSAGEM
            if (publicacao.Mensagem == null || publicacao.Mensagem.length() <= 0) {
                holder.Descricao.setVisibility(View.GONE);
            } else {
                holder.Descricao.setVisibility(View.VISIBLE);
                String msg = publicacao.Mensagem.trim();
                if (publicacao.Photo == null || publicacao.Photo.isEmpty()) {
                    holder.Descricao.setText(msg);
                    holder.Descricao.setMaxLines(20);
                } else {
                    if (msg.length() > 300) {
                        Spanned descricao = Html.fromHtml(msg.substring(0, 300) + "... <font color='#6E008A'>Continuar lendo</font>");
                        holder.Descricao.setText(descricao);
                    } else {
                        holder.Descricao.setText(msg);
                    }
                }

                holder.Descricao.setMovementMethod(LinkMovementMethod.getInstance());
            }

            //Verificar se existe curtidas ou comentarios
            //Adicionar as curtidas e os comentarios caso existe
            if (publicacao.Curtidas > 0 || publicacao.Comentarios > 0) {
                holder.info.setVisibility(View.VISIBLE);

                //Verifica se existe curtidas
                if (publicacao.Curtidas > 0) {
                    holder.icon_curtida.setVisibility(View.VISIBLE);
                    holder.qtd_curtidas.setVisibility(View.VISIBLE);
                    holder.qtd_curtidas.setText(String.valueOf(publicacao.Curtidas));
                }
                //Marca invisivel caso nao exista
                else {
                    holder.icon_curtida.setVisibility(View.INVISIBLE);
                    holder.qtd_curtidas.setVisibility(View.INVISIBLE);
                }

                //Verifica se existe Comentarios
                if (publicacao.Comentarios > 0) {
                    holder.qtd_comentarios.setVisibility(View.VISIBLE);
                    holder.qtd_comentarios.setText(String.valueOf(publicacao.Comentarios) + " comentários");
                }
                //Marca invisivel caso nao exista
                else
                    holder.qtd_comentarios.setVisibility(View.INVISIBLE);
            }
            //Se nao existir curtidas e comentarios a bara de informaçoes desaparece
            else
                holder.info.setVisibility(View.GONE);

            //Verificar se você já curtiu a imagem
            //Adicionar cor primarya no botao mostrando que já curtiu a foto
            if (publicacao.Curtiu) {
                holder.texto_curtir.setText("Curtiu");
                holder.texto_curtir.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                holder.icon_curtir.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            }

            //Voltar o padrao mostrando que nao curtiu a foto
            else {
                holder.texto_curtir.setText("Curtir");
                holder.texto_curtir.setTextColor(ContextCompat.getColor(mContext, R.color.gray6666));
                holder.icon_curtir.setColorFilter(ContextCompat.getColor(mContext, R.color.gray6666));
            }

            if(publicacao.Usuario != null) {
                holder.Nome.setText(publicacao.Usuario.username);
                //LOAD IMAGE USER THUMBNAIL
                Picasso.with(mContext)
                        .load(publicacao.Usuario.avatar_url)
                        .placeholder(R.drawable.noprofile)
                        .error(R.drawable.noprofile)
                        .into(holder.Perfil);
            }
                holder.Horario.setText(publicacao.Data);
            //holder.ImagePublicacao.setImageDrawable(mContext.getResources().getDrawable(R.drawable.nopicture));




            // VERIFICA SE EXISTE IMAGEM A PUBLICAÇÃO
            if (publicacao.Photo == null || publicacao.Photo.isEmpty()) {
                holder.ImagePublicacao.setVisibility(View.GONE);

            }
            // DOWNLOAD IMAGE PUBLISHED
            else {


                //BAIXAR A IMAGEM
                holder.ImagePublicacao.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(publicacao.Photo)
                        .placeholder(R.drawable.noprofile)
                        .error(R.drawable.noprofile)
                        .into(holder.ImagePublicacao);
            }

        }
        //Holder index 0, para adicionar uma nova postagem
        else if (viewHolder instanceof PostagemViewHolder) {
            PostagemViewHolder holder = (PostagemViewHolder) viewHolder;

            //LOAD IMAGE USER AUTENTICATED THUMBNAIL
            Picasso.with(mContext)
                    .load(Service.UserProfile.avatar_url)
                    .placeholder(R.drawable.noprofile)
                    .error(R.drawable.noprofile)
                    .into(holder.Perfil);

            if (postImageUri != null) {
                holder.tb_descricao.setText(postTexto);
                if (postTexto != null)
                    holder.tb_descricao.setSelection(postTexto.length());

                holder.bt_removeImage.setVisibility(View.VISIBLE);
                holder.Img_postagem.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(postImageUri)
                        .into(holder.Img_postagem);
            } else {
                holder.tb_descricao.setText(postTexto);
                if (postTexto != null)
                    holder.tb_descricao.setSelection(postTexto.length());

                holder.Img_postagem.setImageBitmap(null);
                holder.Img_postagem.setVisibility(View.GONE);
                holder.bt_removeImage.setVisibility(View.GONE);
            }
        }
        //Holder loading, verificar se ainda existe novas postagens
        else if (viewHolder instanceof LoadingViewHolder) {
            if (isLoading)
                viewHolder.itemView.setVisibility(View.VISIBLE);
            else {
                viewHolder.itemView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position) && NovaPostagem)
            return TYPE_HEADER;
        else if (isPositionBottom(position))
            return TYPE_LOADING;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionBottom(int position) {
        return position == (mList.size() - 1);
    }

    public Publicacao getItem(int position) {
        Publicacao p = mList.get(position);
        return p;
    }

    public void setListAndClear(List<Publicacao> list) {
        mList.clear();
        isLoading = true;

        //Adiciona o header
        if (NovaPostagem)
            mList.add(new Publicacao());

        mList.addAll(list);

        //Adiciona o loading
        mList.add(new Publicacao());

        notifyDataSetChanged();
    }

    public void addList(List<Publicacao> list) {
        int position = mList.size() - 1;
        mList.addAll(position, list);
        notifyDataSetChanged();
    }


    public void addListItem(Publicacao item, int position) {
        mList.add(item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView Perfil;
        public TextView Nome;
        public TextView Horario;
        public TextView Descricao;
        public ImageView ImagePublicacao;
        public ImageView Opcoes;

        //Likes and comments
        public LinearLayout bt_curtir;
        public ImageView icon_curtir;
        public TextView texto_curtir;
        public LinearLayout bt_comentar;
        public RelativeLayout info;
        public TextView qtd_comentarios;
        public TextView qtd_curtidas;
        public ImageView icon_curtida;

        public FeedViewHolder(View itemView) {
            super(itemView);

            Perfil = (CircleImageView) itemView.findViewById(R.id.img_perfil);
            Nome = (TextView) itemView.findViewById(R.id.tv_nome);
            Horario = (TextView) itemView.findViewById(R.id.tv_horario);
            Descricao = (TextView) itemView.findViewById(R.id.tv_descricao);
            ImagePublicacao = (ImageView) itemView.findViewById(R.id.img_publicacao);
            Opcoes = (ImageView) itemView.findViewById(R.id.iv_opcoes);

            bt_curtir = (LinearLayout) itemView.findViewById(R.id.bt_curtir);
            texto_curtir = (TextView) itemView.findViewById(R.id.texto_curtir);
            icon_curtir = (ImageView) itemView.findViewById(R.id.icon_curtir);
            bt_comentar = (LinearLayout) itemView.findViewById(R.id.bt_comentar);
            info = (RelativeLayout) itemView.findViewById(R.id.ll_info);
            qtd_comentarios = (TextView) itemView.findViewById(R.id.tv_comentarios);
            qtd_curtidas = (TextView) itemView.findViewById(R.id.tv_curtidas);
            icon_curtida = (ImageView) itemView.findViewById(R.id.icon_curtida);

            icon_curtida.setOnClickListener(this);
            qtd_curtidas.setOnClickListener(this);

            qtd_comentarios.setOnClickListener(this);
            ImagePublicacao.setOnClickListener(this);
            Descricao.setOnClickListener(this);
            Perfil.setOnClickListener(this);
            Nome.setOnClickListener(this);
            Horario.setOnClickListener(this);
            Opcoes.setOnClickListener(this);
            bt_curtir.setOnClickListener(this);
            bt_comentar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAdapterOnClickListener.onAdapterOnClickListener(view, getPosition(), getItemViewType());
        }
    }

    public class PostagemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView Perfil;
        public ImageView Img_postagem;
        public ImageView bt_removeImage;
        public EditText tb_descricao;
        public LinearLayout bt_addFoto;


        public PostagemViewHolder(View itemView) {
            super(itemView);

            Perfil = (CircleImageView) itemView.findViewById(R.id.iv_perfil);
            Img_postagem = (ImageView) itemView.findViewById(R.id.img_postagem);
            tb_descricao = (EditText) itemView.findViewById(R.id.tb_texto);
            bt_addFoto = (LinearLayout) itemView.findViewById(R.id.bt_addFoto);
            bt_removeImage = (ImageView) itemView.findViewById(R.id.bt_removeImagem);

            bt_addFoto.setOnClickListener(this);
            bt_removeImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            postTexto = tb_descricao.getText().toString();
            mAdapterOnClickListener.onAdapterOnClickListener(v, getPosition(), getItemViewType());
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);

        }
    }
}
