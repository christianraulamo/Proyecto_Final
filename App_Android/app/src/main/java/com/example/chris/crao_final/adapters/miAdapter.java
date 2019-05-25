package com.example.chris.crao_final.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.crao_final.MainActivity;
import com.example.chris.crao_final.R;
import com.example.chris.crao_final.modelos.Restaurante;
import com.squareup.picasso.Picasso;

import java.util.List;


public class miAdapter extends RecyclerView.Adapter<miAdapter.RestauranteViewHolder> {

    private static Context contexto;
    private int layout;
    List<Restaurante> restaurantes;
    private static int posicion;


    public miAdapter(Context contexto, int layout, List<Restaurante> restaurante) {
        this.contexto = contexto;
        this.layout = layout;
        this.restaurantes = restaurante;
    }


    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View vista = inflater.inflate(this.layout, viewGroup, false);

        RestauranteViewHolder holder = new RestauranteViewHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder restauranteViewHolder, int i) {
        restauranteViewHolder.poblar(restaurantes.get(i));
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public int getPosicion() {
        return posicion;
    }

    public static class RestauranteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView TextViewNombre;
        private ImageView Foto;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            TextViewNombre = itemView.findViewById(R.id.nombreRestaurante);
            Foto = itemView.findViewById(R.id.restauranteFoto);

            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    posicion = getAdapterPosition();
                    return false;
                }
            });
        }


        public void poblar(Restaurante restaurante) {

            TextViewNombre.setText(restaurante.getNombre());

            Picasso.with(contexto)
                    .load(restaurante.getFoto())
                    .fit()
                    .centerCrop()
                    .into(Foto);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater inflater = ((MainActivity) contexto).getMenuInflater();
            inflater.inflate(R.menu.list_restaurante_menu2, contextMenu);

        }


    }


}
