package com.example.yisela.mibusnavidrawer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText usuario, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        usuario = findViewById(R.id.Caja_usuario);
        pass = findViewById(R.id.Caja_pass);
    }


    private void login()
    {
        pasajero p = new pasajero();
        p.setUsuario(usuario.getText().toString().trim());
        p.setContraseña(pass.getText().toString().trim());
        String cuenta = p.getUsuario();
        String pass = p.getContraseña();
        mAuth.signInWithEmailAndPassword(cuenta,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {

                    Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void boton_registrar_main(View view)
    {
        Intent i = new Intent(LoginActivity.this, Registrar.class);
        startActivity(i);
    }

    public void boton_login(View view)
    {
        login();
    }




}

