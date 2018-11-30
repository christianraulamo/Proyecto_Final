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

import com.example.chris.crao_final.modelos.Restaurante;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    List<Restaurante> restaurantes;
    miAdapter adapter;


    private DataSnapshot dataSnapshot;
    private LinearLayoutManager manager ;

    private FirebaseDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rv =(RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        restaurantes = new ArrayList<Restaurante>();

        DatabaseReference database2;
       database = FirebaseDatabase.getInstance();

        adapter = new miAdapter(this ,R.layout.row_recycler, restaurantes) ;

        rv.setAdapter(adapter);

        manager = new LinearLayoutManager(this) ;
        rv.setLayoutManager(manager) ;

        database.getReference("Restaurantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                restaurantes.clear();
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()){

                    Restaurante restaurante = new Restaurante();

                    restaurante = snapshot.getValue(Restaurante.class);
                    restaurante.setIdRestaurante(snapshot.getKey()) ;

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
        //
// Comprobar qué opción ha elegido el usuario
        switch(item.getItemId()) {
            case R.id.InfoRestaurante:

                // Creamos el usuario

                String nombre = restaurantes.get(adapter.getPosicion()).getNombre();
                String foto = restaurantes.get(adapter.getPosicion()).getFoto();
                String descripcion = restaurantes.get(adapter.getPosicion()).getDescripcion();
                String tipo = restaurantes.get(adapter.getPosicion()).getTipo();
                //Restaurante restauranteData = dataSnapshot.getValue(Restaurante.class);


                Bundle bundle = new Bundle();
                bundle.putSerializable("Nombre", nombre);
                bundle.putSerializable("Foto", foto);
                bundle.putSerializable("Descripcion", descripcion);
                bundle.putSerializable("Tipo", tipo);

                Intent intent3 = new Intent(MainActivity.this, InfoActivity.class) ;
                intent3.putExtras(bundle) ;
                startActivity(intent3) ;


                break ;

            case R.id.BorrarRestaurante:

                database.getReference("Restaurantes/" + restaurantes.get(adapter.getPosicion()).getIdRestaurante()).removeValue() ;

                break ;

            default :
                return super.onOptionsItemSelected(item);
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Comprobar qué opción ha elegido el usuario
        switch(item.getItemId()) {
            case R.id.NewRestaurante:
                Intent intent = new Intent(MainActivity.this, NewActivity.class) ;
                startActivity(intent) ;

                break ;

            case R.id.listRestauranteExit:
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();
                break ;

            default :
                return super.onOptionsItemSelected(item);
        }

        return true ;
    }
}
