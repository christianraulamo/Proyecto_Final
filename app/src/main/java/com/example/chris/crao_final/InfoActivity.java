package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private TextView nom, tipoInfo, descripcionInfo, precioMedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Obtención de los datos enviados
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("Nombre");
        String foto = bundle.getString("Foto");
        String descripcion = bundle.getString("Descripcion");
        String tipo = bundle.getString("Tipo");

        //Asociar ImageView a una variable
        ImageView imageView = (ImageView) findViewById(R.id.fotoInfo);

        //Poblar la ImageView con picaso
        Picasso.with(InfoActivity.this).load(foto).fit().centerCrop().into(imageView);

        //Asociar un TextView a una variable
        nom = findViewById(R.id.texNombre);

        //Asociar un texto al TextView
        nom.setText(nombre);

        //Asociar un TextView a una variable
        tipoInfo = findViewById(R.id.textTipo);
        //Asociar un texto al TextView
        tipoInfo.setText("Tipo: " + tipo);

        //Asociar un TextView a una variable
        descripcionInfo = findViewById(R.id.textDescripcion);
        //Asociar un texto al TextView
        descripcionInfo.setText("Descripción:  " + descripcion);

    }

    public void MasInfo(View view) {

        //Obtencion de la variable
        Bundle bundles = getIntent().getExtras();
        String ubicacion = bundles.getString("Ubicacion");
        String telefono = bundles.getString("Telefono");

        //Enviar las variables a la actividadInfoMainActivity
        Bundle bundle = new Bundle();
        bundle.putSerializable("ubicacion", ubicacion);
        bundle.putSerializable("telefono", telefono);

        //Cambio de actividad a InfoMainActivity
        Intent i = new Intent(this, InfoMainActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void goToComent(View view) {

        //Obtencion de la variable
        Bundle bundles = getIntent().getExtras();
        String nombre = bundles.getString("Nombre");
        String Email = bundles.getString("Email");

        //Enviar las variables a la ComentActivity
        Bundle bundle = new Bundle();
        bundle.putSerializable("nombre", nombre);
        bundle.putSerializable("Email", Email);

        //Cambio de actividad a ComentActivity
        Intent e = new Intent(this, ComentActivity.class);
        e.putExtras(bundle);
        startActivity(e);
    }

    //Volver a la actividad anterior
    public void goBack(View view) {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Obtenemos el inflador para inflar el menú
        getMenuInflater().inflate(R.menu.list_restaurante_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobar qué opción ha elegido el usuario
        switch (item.getItemId()) {

            case R.id.listRestauranteExit:
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                // finish();
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.Perfil:

                Bundle bundles = getIntent().getExtras();
                String Email = bundles.getString("Email");

                Bundle bundle = new Bundle();
                bundle.putSerializable("Email", Email);
                Intent intent4 = new Intent(InfoActivity.this, PerfilActivity.class);
                intent4.putExtras(bundle);
                startActivity(intent4);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}