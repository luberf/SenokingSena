package com.example.yisela.mibusnavidrawer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class UbicaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private static final String TAG = "PlaceAutocompleteAd";
    public LatLng miubicacion;
    public LatLng midestino;
    public LatLng direccionorigen = null;
    public LatLng direcciondestino = null;
    double latorigen;
    double lngorigen;
    double latdestino;
    double lngdestino;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubica);
        int permissionCheck = ContextCompat.checkSelfPermission(UbicaActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
        {
        } else
            {
            ActivityCompat.requestPermissions(UbicaActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            }
        LocationManager locationManager = (LocationManager) UbicaActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener()


        {
            @Override
            public void onLocationChanged(Location location)
            {
                latorigen = 2.452412;
                lngorigen = -76.603539;
                miubicacion = new LatLng(latorigen, lngorigen);
                direccionorigen = miubicacion;
                latdestino = 2.455305;
                lngdestino = -76.597174;
                midestino = new LatLng(latdestino, lngdestino);
                direcciondestino = midestino;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }
        };

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latorigen = 2.452412;
        lngorigen = -76.603539;
        miubicacion = new LatLng(latorigen, lngorigen);
        direccionorigen = miubicacion;
        latdestino = 2.455305;
        lngdestino = -76.597174;
        midestino = new LatLng(latdestino, lngdestino);
        direcciondestino = midestino;
    }
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        Marker a = mMap.addMarker(new MarkerOptions().position(direccionorigen).title("Origen"));
        a.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pegmancuatro));
        mMap.addMarker(new MarkerOptions().position(direcciondestino).title("Destino"));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(direccionorigen)   //Centramos el mapa en Popayan
                .zoom(15)         //Establecemos el zoom en 15
                .bearing(45)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
        Polyline rutamuestra = mMap.addPolyline(new PolylineOptions()
                .visible(true)//aqui dice si la polyline es visible true-false un ejemplo
                .add(new LatLng(2.452412, -76.603539), new LatLng(2.455305, -76.597174))
                .width(10)
                .color(Color.RED));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //----------------------------

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else
                    {

                }
                return;
            }
        }
    }


}