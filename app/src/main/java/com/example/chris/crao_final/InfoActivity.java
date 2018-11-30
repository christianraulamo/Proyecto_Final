package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.crao_final.modelos.Restaurante;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TextView nom, tipoInfo, descripcionInfo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle bundle = getIntent().getExtras() ;
        String nombre = bundle.getString("Nombre") ;
        String foto = bundle.getString("Foto") ;
        String descripcion = bundle.getString("Descripcion") ;
        String tipo = bundle.getString("Tipo") ;


        ImageView imageView = (ImageView) findViewById(R.id.fotoInfo);

        Picasso.with(InfoActivity.this).load(foto).into(imageView);

        nom = findViewById(R.id.texNombre) ;
        nom.setText(nombre) ;

        tipoInfo = findViewById(R.id.textTipo) ;
        tipoInfo.setText("Tipo: " + tipo) ;

        descripcionInfo = findViewById(R.id.textDescripcion) ;
        descripcionInfo.setText("Descripción:  " + descripcion) ;
    }
}