package com.camila.ortiz.ortiz_t4;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adaptador extends RecyclerView.Adapter<adaptador.viewPélicula>{

    List<pelicualaGET> list;
    Context activi;

    public adaptador(List<pelicualaGET> list, Context activi) {
        this.list = list;
        this.activi = activi;
    }

    @NonNull
    @Override
    public viewPélicula onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new viewPélicula(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_lis, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewPélicula holder, int position) {

        pelicualaGET get = list.get(position);
        Log.e("ima",get.getImagen_url());
        Picasso.get()
                .load("https://upn.lumenes.tk"+get.getImagen_url())
                .into(holder.imagen);

        holder.nombre.setText(get.nombre);
        holder.LinearLayout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activi,detallae.class);
                intent.putExtra("pelicula",get);
                activi.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewPélicula extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre;
        LinearLayout LinearLayout_id;

        public viewPélicula(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.image);
            nombre = itemView.findViewById(R.id.name);
            LinearLayout_id = itemView.findViewById(R.id.LinearLayout_id);

        }
    }
}
