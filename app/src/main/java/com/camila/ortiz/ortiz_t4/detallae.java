package com.camila.ortiz.ortiz_t4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class detallae extends AppCompatActivity {

    EditText nombre, sinopsis;
    ImageView iamge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallae);

        nombre = findViewById(R.id.nombre);
        sinopsis = findViewById(R.id.vistas);

        iamge = findViewById(R.id.imagen);

        pelicualaGET pelicualaGET = (pelicualaGET) getIntent().getSerializableExtra("pelicula");

        nombre.setText(pelicualaGET.getNombre());
        String vis = String.valueOf(pelicualaGET.vistas);
        sinopsis.setText(vis);

        Picasso.get()
                .load("https://upn.lumenes.tk" + pelicualaGET.getImagen_url())
                .into(iamge);
    }
}