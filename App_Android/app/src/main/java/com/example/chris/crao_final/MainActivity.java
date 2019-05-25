package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chris.crao_final.adapters.miAdapter;
import com.example.chris.crao_final.modelos.Restaurante;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    List<Restaurante> restaurantes;
    miAdapter adapter;

    private LinearLayoutManager manager;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        restaurantes = new ArrayList<Restaurante>();

        //Obtener la instancia de la Firebase RealTime
        database = FirebaseDatabase.getInstance();

        //Obtener el Adapter
        adapter = new miAdapter(this, R.layout.row_recycler, restaurantes);

        rv.setAdapter(adapter);

        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);

        // Poblar el reciclerView
        database.getReference("Restaurantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Vaciar el array
                restaurantes.clear();

                //Recorrer la base de datos de la tabla Restaurante
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Restaurante restaurante = new Restaurante();

                    restaurante = snapshot.getValue(Restaurante.class);
                    restaurante.setIdRestaurante(snapshot.getKey());

                    //Añadir restaurante al array
                    restaurantes.add(restaurante);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Obtenemos el inflador para inflar el menú
        getMenuInflater().inflate(R.menu.list_restaurante_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.InfoRestaurante:

                //Obtencion de las variables
                Bundle bundles = getIntent().getExtras();
                String Email = bundles.getString("Email");

                //Asociar los datos del restaurante seleccionado a variables
                String nombre = restaurantes.get(adapter.getPosicion()).getNombre();
                String foto = restaurantes.get(adapter.getPosicion()).getFoto();
                String descripcion = restaurantes.get(adapter.getPosicion()).getDescripcion();
                String tipo = restaurantes.get(adapter.getPosicion()).getTipo();
                String ubicacion = restaurantes.get(adapter.getPosicion()).getUbicacion();
                String telefono = restaurantes.get(adapter.getPosicion()).getTelefono();
                String IdRestaurante = restaurantes.get(adapter.getPosicion()).getIdRestaurante();

                //Enviar los datos del restaurante a la actividad InfoActivity
                Bundle bundle = new Bundle();
                bundle.putSerializable("Nombre", nombre);
                bundle.putSerializable("Foto", foto);
                bundle.putSerializable("Descripcion", descripcion);
                bundle.putSerializable("Tipo", tipo);
                bundle.putSerializable("Ubicacion", ubicacion);
                bundle.putSerializable("Telefono", telefono);
                bundle.putSerializable("IdRestaurante", IdRestaurante);
                bundle.putSerializable("Email", Email);

                //Cambiar a la actividad InfoActivity
                Intent intent3 = new Intent(MainActivity.this, InfoActivity.class);
                intent3.putExtras(bundle);
                startActivity(intent3);

                break;

            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobar qué opción ha elegido el usuario
        switch (item.getItemId()) {

            case R.id.listRestauranteExit:
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                // finish();
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.Perfil:

                //Recoger el dato mandado
                Bundle bundles = getIntent().getExtras();
                String Email = bundles.getString("Email");

                //Mandar la variable a la otra actividad
                Bundle bundle = new Bundle();
                bundle.putSerializable("Email", Email);

                //Ir a la otra actividad
                Intent intent4 = new Intent(MainActivity.this, PerfilActivity.class);
                intent4.putExtras(bundle);
                startActivity(intent4);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    @Override
    public void onBackPressed() {
    }

}
