package com.example.patrick.loginauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.patrick.loginauth.config.ConfiguracaoFirebase;
import com.example.patrick.loginauth.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_usuario;
    private EditText editText_senha;
    private EditText editText_confirmarSenha;
    private EditText editText_email;
    private Button bt_cadastrar;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);



        editText_usuario = (EditText) findViewById(R.id.editText_usuario);
        editText_senha = (EditText) findViewById(R.id.editText_senha);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_confirmarSenha = (EditText) findViewById(R.id.editText_confirmarSenha);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        bt_cadastrar.setOnClickListener(this);
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();

    }

    private void registrarUsuario(){
        String email =  editText_email.getText().toString();
        String senha =  editText_senha.getText().toString();
        String nome =  editText_usuario.getText().toString();
        String confirmarsenha =  editText_confirmarSenha.getText().toString();

        final Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNome(nome);

        /*
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor insira o email", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(senha)){
            Toast.makeText(this, "Por favor insira a senha", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(confirmarsenha)){
            Toast.makeText(this, "Por favor confirme a senha", Toast.LENGTH_LONG).show();

        }

        progressDialog.setMessage("Registrando usuário...");
        progressDialog.show();
*/

        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser usuarioFirebase = task.getResult().getUser();
                            usuario.setId(usuarioFirebase.getUid());
                            usuario.salvar();

                            Toast.makeText(getApplicationContext(), "Registro feito com sucesso", Toast.LENGTH_LONG).show();
                            FirebaseAuth fAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
                            fAuth.signOut();
                            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));

                        }
                        else {
                            String erro = "";
                            try{
                                throw(task.getException());
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "A senha deve possuir no mínimo 6 caracteres";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                               erro = "O email digitado não é válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "Esse email já está sendo usado";
                            } catch (Exception e) {
                                erro = "Erro ao registrar";
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        registrarUsuario();
    }
}
