package com.example.patrick.loginauth.model;

import com.example.patrick.loginauth.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by Patrick on 13/06/2017.
 */

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;




    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario() {


    }
    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("usuarios").child(getId()).setValue(this);


    }
}
