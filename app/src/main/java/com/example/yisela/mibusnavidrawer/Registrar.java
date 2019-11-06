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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity
{
    private EditText correo, contraseña,nombre;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private Boolean tarea=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        correo = findViewById(R.id.Caja_correo);
        contraseña = findViewById(R.id.Caja_contraseña);
        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.caja_nombre);

    }

    private void registrar_1 ()
    {

        String cuenta = correo.getText().toString().trim();
        String pass = contraseña.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(cuenta,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    final Map<String, Object> latlang = new HashMap<>();
                   Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    ref = database.getReference("usuario");

                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("Correo",correo.getText().toString().trim());
                    usuario.put("Nombre",nombre.getText().toString().trim());
                    tarea=true;
                    ref.push().setValue(usuario);

                    if (tarea==true)
                    {
                        Intent i = new Intent(Registrar.this, LoginActivity.class);
                        startActivity(i);
                        tarea=false;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Usuario no creado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void boton_registrar(View view)
    {
        registrar_1();
    }
}
