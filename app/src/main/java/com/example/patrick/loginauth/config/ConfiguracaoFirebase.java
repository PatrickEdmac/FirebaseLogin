package com.example.patrick.loginauth.config;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Patrick on 13/06/2017.
 */

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaDatabase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){
        if(referenciaDatabase == null){
            referenciaDatabase = FirebaseDatabase.getInstance().getReference();
        }
    return referenciaDatabase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
