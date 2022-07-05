package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.camila.ortiz.ortiz_t4.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        String latitud = getIntent().getStringExtra("latitud") ;
        String longitud = getIntent().getStringExtra("longitud") ;
        String nombre = getIntent().getStringExtra("nombre") ;

        LatLng sydney = new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud));
        googleMap.addMarker(new MarkerOptions().position(sydney).title(nombre));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f));

    }
}