package com.frezarin.campusparty.API.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.frezarin.campusparty.Utils.Service;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 02/02/2017.
 */

@JsonRootName("post")
public class Publicacao implements Serializable {

    public Publicacao(){}
    public Publicacao(int id, String msg, Profile user, String photo, String created_at, int likes, int qtdComment, boolean dono, boolean liked){
        Id = id;
        Usuario = user;
        Mensagem = msg;
        Photo = photo;
        Data = created_at;
        Curtidas = likes;
        Comentarios = qtdComment;
        DonoPost = dono;
        Curtiu = liked;
    }

    @SerializedName("id")
    public int Id;

    @SerializedName("user")
    public Profile Usuario;

    @SerializedName("message")
    public String Mensagem;

    @SerializedName("photo")
    public String Photo;

    @SerializedName("created_at")
    public String Data;

    @SerializedName("likes_count")
    public int Curtidas;

    @SerializedName("comments_count")
    public int Comentarios;

    @SerializedName("own_post")
    public boolean DonoPost;

    @SerializedName("liked")
    public boolean Curtiu;

    public static List<Publicacao> loadFakePosts(){
        List<Publicacao> posts = new ArrayList<>();
        posts.add(new Publicacao(1,
                "Campus Party Brazil is the best!!!",
                Profile.loadFakeUsers().get(0),
                "https://www.promoview.com.br/uploads/2017/11/images/05.11/Campus_Party_BSB_de_2018_ocorrera_no_Mane_Garrincha_1.jpg",
                "Agora mesmo",
                239,
                96,
                true,
                false));

        posts.add(new Publicacao(2,
                "#SUPERCOOL!",
                Profile.loadFakeUsers().get(1),
                null,
                "Agora mesmo",
                52,
                29,
                true,
                false));

        return posts;
    }
}

