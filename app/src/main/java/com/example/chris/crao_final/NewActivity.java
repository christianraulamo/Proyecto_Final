package com.example.chris.crao_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chris.crao_final.modelos.Restaurante;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewActivity extends AppCompatActivity {

    EditText Nombre, Tipo, descripcion, telefono, ubicacion;
    String foto;
    Button guardar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Nombre = findViewById(R.id.newNameRestaurant) ;
        Tipo = findViewById(R.id.newTipeRestaurant);
        descripcion = findViewById(R.id.newdescriptionRestaurant);
        telefono = findViewById(R.id.newtelefonoRestaurant);
        foto = "https://firebasestorage.googleapis.com/v0/b/crao-aaedc.appspot.com/o/mammamia.jpg?alt=media&token=34567c2a-d992-476e-91cb-615275a07f98";

        guardar = findViewById(R.id.crearRestaurante);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  nom = Nombre.getText().toString();
                String  tip = Tipo.getText().toString();
                String  des = descripcion.getText().toString();
                String  ubi = "geo:0,0?q=" + Nombre.getText().toString();
                String  tel = telefono.getText().toString();

                String id = databaseReference.push().getKey();

                Restaurante rest = new Restaurante(nom, tip, des, foto, ubi, tel);

                databaseReference.child("Restaurantes").child(id).setValue(rest);

                Intent intent = new Intent(NewActivity.this, MainActivity.class) ;
                startActivity(intent) ;
            }
        });
    }
}
