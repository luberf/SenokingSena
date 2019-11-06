package com.example.yisela.mibusnavidrawer;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configura);
        getSupportActionBar().setTitle("Configuracion"); //Configura el texto del Toolbar del Activity ConfiguraActivity
    }

    public void cerrar_sesion(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Cerro la sesion", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
