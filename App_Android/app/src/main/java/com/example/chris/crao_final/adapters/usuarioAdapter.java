package com.example.chris.crao_final.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.crao_final.modelos.Usuario;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class usuarioAdapter extends RecyclerView.Adapter<usuarioAdapter.UsuarioViewHolder> {

    private static Context contexto;
    private int layout;
    List<Usuario> usuarios;

    public usuarioAdapter(Context contexto, int layout, List<Usuario> usuario) {
        this.contexto = contexto;
        this.layout = layout;
        this.usuarios = usuario;
    }

    public usuarioAdapter(ValueEventListener valueEventListener, int view_recycler, List<Usuario> usuarios) {
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View vista = inflater.inflate(this.layout, viewGroup, false);

        usuarioAdapter.UsuarioViewHolder holder = new usuarioAdapter.UsuarioViewHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder usuarioViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
