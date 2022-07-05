package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.camila.ortiz.ortiz_t4.R;
import com.camila.ortiz.ortiz_t4.servicio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class todosActivity extends AppCompatActivity {

    FloatingActionButton registrar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        init();

        registrar.setOnClickListener(v -> startActivity(new Intent(todosActivity.this, crearNuevoActivity.class)));

        getLibros();
    }


    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final servicio service = retrofit.create(servicio.class);

    private void init() {
        registrar = findViewById(R.id.registrar);
        mRecyclerView = findViewById(R.id.recyclerViewUser);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getLibros() {
        Call<List<libro>> listGet = service.getListLibros();
        listGet.enqueue(new Callback<List<libro>>() {
            @Override
            public void onResponse(@NonNull Call<List<libro>> call, @NonNull Response<List<libro>> response) {
                if (response.code() == 200) {
                    List<libro> myList = response.body();
                    adapterLibro adapterUser = new adapterLibro(myList, todosActivity.this);
                    mRecyclerView.setAdapter(adapterUser);
                } else {
                    Log.e("errRe ", response.toString());

                    Toast.makeText(todosActivity.this, "Error al obtener, codigo de error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<libro>> call, @NonNull Throwable t) {
                Log.e("errRe ", t.getMessage());

                Toast.makeText(todosActivity.this, "Error al obtener, codigo de error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}