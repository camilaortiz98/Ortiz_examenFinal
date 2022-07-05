package com.camila.ortiz.ortiz_t4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        RecyclerView recyclerView = findViewById(R.id.lista);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio peliculas = retrofit.create(servicio.class);

        Call<List<pelicualaGET>> listGet = peliculas.mostrrListaPelicualas();

        listGet.enqueue(new Callback<List<pelicualaGET>>() {
            @Override
            public void onResponse(Call<List<pelicualaGET>> call, Response<List<pelicualaGET>> response) {
                String code = String.valueOf(response.code());
                if (code.equals("200")) {
                    List<pelicualaGET> list = response.body();
                    adaptador adapterList = new adaptador(list, listaActivity.this);
                    recyclerView.setAdapter(adapterList);
                }
            }

            @Override
            public void onFailure(Call<List<pelicualaGET>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR REVISAR ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}