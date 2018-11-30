package com.example.chris.crao_final;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.crao_final.modelos.Restaurante;
import com.squareup.picasso.Picasso;

import java.util.List;

public class miAdapterInfo extends RecyclerView.Adapter<miAdapterInfo.RestauranteViewHolder>{

    private static Context contexto ;
    private int layout ;
    List<Restaurante> restaurantes;

    public miAdapterInfo(Context contexto, int layout, List<Restaurante> restaurante) {
        this.contexto = contexto;
        this.layout = layout;
        this.restaurantes = restaurante;
    }

    @NonNull
    @Override
    public miAdapterInfo.RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext()) ;

        View vista = inflater.inflate(this.layout , viewGroup,false) ;

        miAdapterInfo.RestauranteViewHolder holder = new miAdapterInfo.RestauranteViewHolder(vista) ;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull miAdapterInfo.RestauranteViewHolder restauranteViewHolder, int i) {
        restauranteViewHolder.poblar(restaurantes.get(i));
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public static class RestauranteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView TextViewNombre, info;
        private ImageView Foto;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            TextViewNombre = itemView.findViewById(R.id.nombreRestaurante);
            Foto = itemView.findViewById(R.id.restauranteFoto) ;
            // info = itemView.findViewById(R.id.infoRestaurante);

            itemView.setOnCreateContextMenuListener(this);
        }


        public void poblar(Restaurante restaurante) {

            TextViewNombre.setText(restaurante.getNombre());
            info.setText(restaurante.getDescripcion());

            String imagen = "R.drawable." + restaurante.getFoto() ;

            Picasso.with(contexto)
                    .load(restaurante.getFoto())
                    .fit()
                    .centerCrop()
                    .into(Foto) ;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater inflater = ((InfoActivity) contexto).getMenuInflater() ;
            inflater.inflate(R.menu.list_restaurante_menu, contextMenu) ;

        }
    }


}