package com.example.yisela.mibusnavidrawer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginCorrecto extends AppCompatActivity
{
    private TextView correo,nombre_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_correcto);
        correo = findViewById(R.id.Correo);
        nombre_sesion = findViewById(R.id.Nombre);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email;
        if (user!=null)
        {

            Toast.makeText(this, "Sesion activa", Toast.LENGTH_SHORT).show();
            email = user.getEmail();
            correo.setText(email);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getInstance().getReference();

            ref.child("usuario").orderByChild("Correo").equalTo(email).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {
                    String nombre = dataSnapshot.child("Nombre").getValue().toString();
                    nombre_sesion.setText(nombre);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
                {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });

        }
        else
            {
                Toast.makeText(this, "Error de sesion", Toast.LENGTH_SHORT).show();
        }
    }

  public void cerrar_sesion(View view)
  {
      FirebaseAuth.getInstance().signOut();
      Toast.makeText(this, "Cerro la sesion", Toast.LENGTH_SHORT).show();
      correo.setText("");
      Intent i = new Intent(this, MainActivity.class);
      startActivity(i);
  }





}
