package com.example.yisela.mibusnavidrawer;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EsperarActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarker = new ArrayList<>();
    private ArrayList<Marker> realTimeMarker = new ArrayList<>();
    private CountDownTimer realTimeTimer;
    boolean actualPosition =true;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esperar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        countDownTimer();
    }

    private void countDownTimer() {
        if (realTimeTimer != null) {
            realTimeTimer.cancel();
        }
        realTimeTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(EsperarActivity.this, "Ubicaciones Actualizadas", Toast.LENGTH_SHORT).show();
                marcarRutas();
            }
        }.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        rutafragment();
        marcarRutas();
        generarTrayecto();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    public void generarTrayecto()
    {
        String url ="https://maps.googleapis.com/maps/api/directions/json?origin=2.455444,-76.597723&destination=2.438236, -76.616210&key=AIzaSyAV1wiq0j16AKY_BYzlCNLnzE3Ykvee2r8";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    jso = new JSONObject(response);
                    trazarRuta(jso);
                    Log.i("jsonRuta: ", "" + response);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        });
        queue.add(stringRequest);
    }


    public void marcarRutas()
    {
        mDatabase.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker : realTimeMarker) {
                    marker.remove();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Marker c;
                    MapsPojo mp = snapshot.getValue(MapsPojo.class);
                    Double latitud = mp.getLatitud();
                    Double longitud = mp.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud, longitud));
                    tmpRealTimeMarker.add(c = mMap.addMarker(markerOptions));
                    c.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bustres));
                }
                realTimeMarker.clear();
                realTimeMarker.addAll(tmpRealTimeMarker);
                countDownTimer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void trazarRuta(JSONObject jso)
    {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++)
            {
                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++)
                {
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++)
                    {
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.RED).width(15));
                    }
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public void rutafragment()
    {
        Bundle extras = getIntent().getExtras();
        LatLng objLatLngorigenfinal = extras.getParcelable("Latlngorigenfinal");
        LatLng objLatLngdestinofinal = extras.getParcelable("Latlngdestinofinal");
        if (objLatLngorigenfinal != null && objLatLngdestinofinal != null )
        {
            Marker a = mMap.addMarker(new MarkerOptions().position(objLatLngorigenfinal).title("Origen"));
            a.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pegmancuatro));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(objLatLngorigenfinal, 15));
            mMap.addMarker(new MarkerOptions().position(objLatLngdestinofinal).title("Destino"));
            /*try
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(objLatLngorigenfinal, objLatLngdestinofinal), 260));
            }
            catch(Exception e)
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(objLatLngdestinofinal, objLatLngorigenfinal), 260));
            }*/
        }
    }
}
