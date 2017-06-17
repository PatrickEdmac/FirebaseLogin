package com.example.patrick.loginauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patrick.loginauth.config.ConfiguracaoFirebase;
import com.example.patrick.loginauth.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText et_email;
    private EditText et_senha;
    private Button bt_cadastrar;
    private Button bt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bt_login = (Button) findViewById(R.id.bt_login);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        bt_login.setOnClickListener(this);
        bt_cadastrar.setOnClickListener(this);





    }
    @Override
    public void onClick(View view) {



        if(view == bt_cadastrar)
        {
            abrirTelaCadastro();
        }
        if(view == bt_login)
        {
            validarLogIn();

        }

    }
    private void validarLogIn(){

        et_email = (EditText) findViewById(R.id.et_email);
        et_senha = (EditText) findViewById(R.id.et_senha);

        Usuario usuario = new Usuario();
        usuario.setEmail(et_email.getText().toString());
        usuario.setSenha(et_senha.getText().toString());

        FirebaseAuth fAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        fAuth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.e("validar login", "validar login");
                    Toast.makeText(getApplicationContext(),"login feito com sucesso",Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(getApplicationContext(),"erro ao efetuar login",Toast.LENGTH_LONG);
                }
            }

        });

    }
    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);

    }
    private void abrirTelaCadastro(){
        Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(intent);
    }
}
