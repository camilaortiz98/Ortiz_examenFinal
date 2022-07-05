package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.camila.ortiz.ortiz_t4.R;
import com.camila.ortiz.ortiz_t4.data;
import com.camila.ortiz.ortiz_t4.responseBody;
import com.camila.ortiz.ortiz_t4.servicio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class crearNuevoActivity extends AppCompatActivity {

    EditText titulo, resumenn, latitud, longitud;
    ImageView imagen;
    Button registrar;
    String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nuevo);

        init();

        registrar.setOnClickListener(v -> registrarImage());
        imagen.setOnClickListener(v -> uploadGallery());

    }

    private static final Retrofit builderImage = new Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final Retrofit builderData = new Retrofit.Builder()
            .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    servicio imageService = builderImage.create(servicio.class);
    servicio libroService = builderData.create(servicio.class);

    private void init() {
        titulo = findViewById(R.id.titulo);
        resumenn = findViewById(R.id.resumenn);
        latitud = findViewById(R.id.latitud);
        longitud = findViewById(R.id.longitud);
        imagen = findViewById(R.id.imagen);
        registrar = findViewById(R.id.registrar);
    }

    private void registrarImage() {

        Call<responseBody> upload = imageService.imageUpload("Client-ID 8bcc638875f89d9", new bodyImage(imageString));
        upload.enqueue(new Callback<responseBody>() {
            @Override
            public void onResponse(@NonNull Call<responseBody> call, @NonNull Response<responseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(crearNuevoActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    responseBody dataRes = response.body();
                    assert dataRes != null;
                    data da = dataRes.getData();
                    Log.e("url ", da.getLink());
                    registrarLibro(da.getLink());

                } else {
                    Log.e("errRe ", response.toString());

                    Toast.makeText(crearNuevoActivity.this, "Error al registrar, codigo de error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<responseBody> call, @NonNull Throwable t) {
                Log.e("onFailure ", t.getMessage());
                Toast.makeText(crearNuevoActivity.this, "Error al registrar, codigo de error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    ActivityResultLauncher<Intent> sIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;

                    Uri uri = data.getData();
                    imagen.setImageURI(uri);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] image = outputStream.toByteArray();
                        imageString = Base64.encodeToString(image, Base64.DEFAULT);
                        Log.e("Base64", imageString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    );

    private void registrarLibro(String link) {

        String tituloS = titulo.getText().toString();
        String resumennS = resumenn.getText().toString();
        String latitudS = latitud.getText().toString();
        String longitudS = longitud.getText().toString();

        libro newLibro = new libro();
        newLibro.setImagen(link);
        newLibro.setTitulo(tituloS);
        newLibro.setResumen(resumennS);
        newLibro.setLatitud(latitudS);
        newLibro.setLongitud(longitudS);
        newLibro.setIdFavorito("");


        Call<Void> upload = libroService.postCreatelibro(newLibro);
        upload.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 201) {
                    Toast.makeText(crearNuevoActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Log.e("errRe ", response.toString());
                    Toast.makeText(crearNuevoActivity.this, "Error al registrar, codigo de error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("onFailure ", t.getMessage());
                Toast.makeText(crearNuevoActivity.this, "Error al registrar, codigo de error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "choose a file");
        sIntentActivityResultLauncher.launch(intent);
    }
}