package com.example.yisela.mibusnavidrawer;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class PagoFragment extends Fragment
{


    public PagoFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pago,container, false);
        Button button = view.findViewById(R.id.btnpago);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "Pago realizado con exito", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), UbicaActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

}
