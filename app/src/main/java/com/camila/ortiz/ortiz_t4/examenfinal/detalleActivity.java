package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.camila.ortiz.ortiz_t4.R;
import com.camila.ortiz.ortiz_t4.servicio;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class detalleActivity extends AppCompatActivity {

    EditText titulo, resumenn;
    ImageView imagen;
    Button Editar;
    Button ver;
    Button favorito;
    Button quitar;

    String latitud;
    String longitud;
    String nombre;
    libro li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        String id = getIntent().getStringExtra("id");
        Log.e("id", id);
        init();


        getLibroDetail(id);
        Editar.setOnClickListener(v -> {
            Intent intent = new Intent(detalleActivity.this, editatActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        ver.setOnClickListener(v -> {
            Intent intent = new Intent(detalleActivity.this, MapsActivity.class);
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud", longitud);
            intent.putExtra("nombre", nombre);
            startActivity(intent);
        });

        favorito.setOnClickListener(v -> marcarFavorito(id));
        quitar.setOnClickListener(v -> quitatFavorito(li.idFavorito));
    }


    private static final Retrofit builderData = new Retrofit.Builder()
            .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    servicio libroService = builderData.create(servicio.class);


    private void init() {
        titulo = findViewById(R.id.titulo);
        resumenn = findViewById(R.id.resumenn);
        imagen = findViewById(R.id.imagen);
        Editar = findViewById(R.id.Editar);
        ver = findViewById(R.id.ver);
        favorito = findViewById(R.id.favorito);
        favorito.setVisibility(View.GONE);
        quitar = findViewById(R.id.quitar);
        quitar.setVisibility(View.GONE);

    }

    private void getLibroDetail(String id) {
        Call<libro> getUser = libroService.getLibro(id);
        getUser.enqueue(new Callback<libro>() {
            @Override
            public void onResponse(@NonNull Call<libro> call, @NonNull Response<libro> response) {
                if (response.code() == 200) {

                    li = response.body();
                    assert li != null;
                    titulo.setText(li.getTitulo());
                    resumenn.setText(li.getResumen());
                    latitud = li.getLatitud();
                    longitud = li.getLongitud();
                    nombre = li.getTitulo();

                    Picasso.get()
                            .load(li.getImagen())
                            .into(imagen, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    imagen.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    imagen.setVisibility(View.VISIBLE);
                                    imagen.setImageResource(R.drawable.ic_launcher_background);
                                }
                            });
                    if (li.isFavorito()) {
                        favorito.setVisibility(View.GONE);
                        quitar.setVisibility(View.VISIBLE);
                    } else {
                        favorito.setVisibility(View.VISIBLE);
                        quitar.setVisibility(View.GONE);
                    }

                } else {

                    Toast.makeText(detalleActivity.this, "Error al obtener libro", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<libro> call, @NonNull Throwable t) {
                Toast.makeText(detalleActivity.this, "Error al obtener libro", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }

    private void marcarFavorito(String id) {
        Call<libro> getUser = libroService.postCreateFavorito(li);
        getUser.enqueue(new Callback<libro>() {
            @Override
            public void onResponse(@NonNull Call<libro> call, @NonNull Response<libro> response) {
                Log.e("resp", response.toString());
                if (response.code() == 201) {
                    libro rs = response.body();
                    assert rs != null;
                    Log.e("idF", rs.idFavorito);
                    updateLibro(rs.id, id, true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<libro> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateLibro(String idF, String id, boolean state) {
        Log.e("idF", idF);
        libro libro = new libro();
        libro.setIdFavorito(idF);
        libro.setFavorito(state);

        Call<ResponseBody> getUser = libroService.updateLibro(libro, id);
        Log.e("aqui", "1");
        getUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.e("aqui", "2");
                    Toast.makeText(detalleActivity.this, "libro actualizado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(detalleActivity.this, "Error al actualizar libro", Toast.LENGTH_LONG).show();
                }
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(detalleActivity.this, "Error al actualizar libro", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }

    private void quitatFavorito(String id) {
        Call<ResponseBody> getUser = libroService.deleteFavorito(id);
        getUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    updateLibro("", li.id, false);
                } else {
                    Toast.makeText(detalleActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(detalleActivity.this, "Error", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
}