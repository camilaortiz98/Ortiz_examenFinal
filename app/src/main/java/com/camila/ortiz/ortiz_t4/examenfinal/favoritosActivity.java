package com.camila.ortiz.ortiz_t4.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.camila.ortiz.ortiz_t4.R;
import com.camila.ortiz.ortiz_t4.servicio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class favoritosActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        init();
        getFavorito();
    }

    private void init() {

        mRecyclerView = findViewById(R.id.recyclerViewUserFavorito);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://62a9caa9371180affbc7d94a.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final servicio service = retrofit.create(servicio.class);

    private void getFavorito() {
        Call<List<libro>> listGet = service.getListFavoritos();
        listGet.enqueue(new Callback<List<libro>>() {
            @Override
            public void onResponse(@NonNull Call<List<libro>> call, @NonNull Response<List<libro>> response) {
                if (response.code() == 200) {
                    List<libro> myList = response.body();
                    adapterLibroFavorito adapterUser = new adapterLibroFavorito(myList);
                    mRecyclerView.setAdapter(adapterUser);
                } else {
                    Log.e("errRe ", response.toString());

                    Toast.makeText(favoritosActivity.this, "Error al obtener, codigo de error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<libro>> call, @NonNull Throwable t) {
                Log.e("errRe ", t.getMessage());

                Toast.makeText(favoritosActivity.this, "Error al obtener, codigo de error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}