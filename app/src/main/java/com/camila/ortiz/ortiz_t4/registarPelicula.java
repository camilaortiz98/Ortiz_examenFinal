package com.camila.ortiz.ortiz_t4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registarPelicula extends AppCompatActivity {

    EditText nombre,sinopsis;
    ImageView iamge;
    Button subirImagen;
    Button registrar;
    String imageS;
    Uri imageU;

    private static final int PICk = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_pelicula);

        nombre = findViewById(R.id.nombre);
        sinopsis = findViewById(R.id.sinopsis);

        iamge = findViewById(R.id.imagen);
        subirImagen = findViewById(R.id.subirimagen);
        registrar = findViewById(R.id.publicar);

        subirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirImagenF();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicio peliculas = retrofit.create(servicio.class);

                String nonbreS = nombre.getText().toString();
                String vistasS = sinopsis.getText().toString();

                Integer integer = Integer.parseInt(vistasS);

                pelicuala pelicua = new pelicuala();
                pelicua.setNombre(nonbreS);
                pelicua.setVistas(integer);
                pelicua.setImagen(imageS);

                Call<Void> resp = peliculas.publicarPelicula(pelicua);

                resp.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        String respuesta = String.valueOf(response.code());
                        if (respuesta.equals("200")) {
                            onBackPressed();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void subirImagenF() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //noinspection deprecation
        startActivityForResult(intent, PICk);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICk) {
            imageU = data.getData();
            iamge.setImageURI(imageU);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageU);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] image = outputStream.toByteArray();
                imageS = Base64.encodeToString(image, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

