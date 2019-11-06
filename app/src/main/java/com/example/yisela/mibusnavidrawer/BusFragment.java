package com.example.yisela.mibusnavidrawer;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class BusFragment extends Fragment {

    View vista;
    Button verificartray;
    Button solicitarbus;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista = inflater.inflate(R.layout.fragment_bus, container, false);
        verificartray=vista.findViewById(R.id.vertray);
        verificartray.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "Trayecto proyectado", Toast.LENGTH_SHORT).show();
            }
        });
        solicitarbus=vista.findViewById(R.id.solbus);
        solicitarbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "La ruta ya pasa por ti", Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }


    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
