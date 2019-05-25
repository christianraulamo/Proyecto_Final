package com.example.chris.crao_final.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.crao_final.R;
import com.example.chris.crao_final.modelos.Comentario;

import java.util.List;

public class comentarioAdapter extends RecyclerView.Adapter<comentarioAdapter.ComentarioViewHolder> {

    private static Context contexto;
    private int layout;
    List<Comentario> comentarios;
    private static int posicion;

    public comentarioAdapter(Context contexto, int layout, List<Comentario> comentario) {
        this.contexto = contexto;
        this.layout = layout;
        this.comentarios = comentario;
    }

    @NonNull
    @Override
    public comentarioAdapter.ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View vista = inflater.inflate(this.layout, viewGroup, false);

        ComentarioViewHolder holder = new ComentarioViewHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder comentarioViewHolder, int i) {
        comentarioViewHolder.poblado(comentarios.get(i));
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public int getPosicion() {
        return posicion;
    }

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre, comentarios, puntuacion, precioMedio;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreUsuario);
            comentarios = itemView.findViewById(R.id.comentario);
            puntuacion = itemView.findViewById(R.id.puntuacion);
            precioMedio = itemView.findViewById(R.id.precioMedio);
        }

        public void poblado(Comentario comentario) {
            nombre.setText(comentario.getUsuario());
            comentarios.setText("Comentario: " + comentario.getComenta());
            puntuacion.setText("Puntuacion: " + comentario.getPuntua());
            precioMedio.setText("Coste de la comida: " + comentario.getPrecioMedio() + "€");
        }
    }
}
