package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.camila.ortiz.ortiz_t4.R;
import com.camila.ortiz.ortiz_t4.servicio;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class editatActivity extends AppCompatActivity {

    EditText titulo, resumenn, latitud, longitud;
    Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editat);
        init();
        String id = getIntent().getStringExtra("id");
        Log.e("id", id);
        getLibroDetail(id);

        actualizar.setOnClickListener(v -> updateLibro(id));
    }


    private void init() {
        titulo = findViewById(R.id.titulo);
        resumenn = findViewById(R.id.resumenn);
        latitud = findViewById(R.id.latitud);
        longitud = findViewById(R.id.longitud);
        actualizar = findViewById(R.id.actualizar);

    }

    private static final Retrofit builderData = new Retrofit.Builder()
            .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    servicio libroService = builderData.create(servicio.class);

    private void getLibroDetail(String id) {
        Call<libro> getUser = libroService.getLibro(id);
        getUser.enqueue(new Callback<libro>() {
            @Override
            public void onResponse(@NonNull Call<libro> call, @NonNull Response<libro> response) {
                if (response.code() == 200) {

                    libro li = response.body();
                    assert li != null;
                    titulo.setText(li.getTitulo());
                    resumenn.setText(li.getResumen());
                    latitud.setText(li.getLatitud());
                    longitud.setText(li.getLongitud());

                } else {

                    Toast.makeText(editatActivity.this, "Error al obtener libro", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<libro> call, @NonNull Throwable t) {
                Toast.makeText(editatActivity.this, "Error al obtener libro", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }

    private void updateLibro(String id) {

        String tituloS = titulo.getText().toString();
        String resumennS = resumenn.getText().toString();
        String latitudS = latitud.getText().toString();
        String longitudS = longitud.getText().toString();

        libro newLibro = new libro();
        newLibro.setTitulo(tituloS);
        newLibro.setResumen(resumennS);
        newLibro.setLatitud(latitudS);
        newLibro.setLongitud(longitudS);


        Call<ResponseBody> getUser = libroService.updateLibro(newLibro, id);

        getUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(editatActivity.this, "libro actualizado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(editatActivity.this, "Error al actualizar libro", Toast.LENGTH_LONG).show();
                }
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(editatActivity.this, "Error al actualizar libro", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
}