package com.example.yisela.mibusnavidrawer;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.view.View.GONE;

public class ListHotelActivity extends AppCompatActivity {
    Button reservar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hotel);
        reservar = findViewById(R.id.reservar);
        reservar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reservar.setVisibility(GONE);
                FragmentManager fm= getSupportFragmentManager();
                PagoFragment fragment= new PagoFragment();
                fm.beginTransaction().replace(R.id.card_view,fragment).commit();

            }
        });
    }


}
