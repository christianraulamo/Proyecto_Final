package com.example.chris.crao_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.crao_final.adapters.comentarioAdapter;
import com.example.chris.crao_final.modelos.Comentario;
import com.example.chris.crao_final.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComentActivity extends AppCompatActivity {

    RecyclerView rv2;
    List<Comentario> comentarios;
    List<Usuario> usuarios;
    comentarioAdapter adapter;
    private EditText precio, coment;
    private ImageButton Btn1, Btn2, Btn3, Btn4, Btn5;

    private LinearLayoutManager manager;

    private DatabaseReference databaseReference;

    private FirebaseDatabase database;

    private FirebaseFirestore dbs;
    public String puntuacion = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);

        //Ocultar el teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().hide();

        //Instanciamos Firebase Realtime
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Asociar el reciclerView a una variable
        rv2 = (RecyclerView) findViewById(R.id.recyclerView);

        rv2.setLayoutManager(new LinearLayoutManager(this));

        //Creacion de un array
        comentarios = new ArrayList<Comentario>();
        usuarios = new ArrayList<Usuario>();

        DatabaseReference database2;

        //Instanciar Realtime Database
        database = FirebaseDatabase.getInstance();

        adapter = new comentarioAdapter(this, R.layout.view_recycler, comentarios);

        rv2.setAdapter(adapter);

        manager = new LinearLayoutManager(this);
        rv2.setLayoutManager(manager);

        //Obtención de la variable
        Bundle bundles = getIntent().getExtras();
        String nombre = bundles.getString("nombre");


        //Obtención de la tabla Comentarios de Realtime Database
        database.getReference("Comentarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Obtención de la variable
                Bundle bundles = getIntent().getExtras();
                String nombre = bundles.getString("nombre");

                //Vaciar el arrray
                comentarios.clear();

                //Recorrer toda la tabla Comentarios
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Comentario comentario = new Comentario();

                    comentario = snapshot.getValue(Comentario.class);
                    comentario.setIdComentario(snapshot.getKey());

                    //Obtener el campo Restaurante de Realtime Database de la tabla Comentarios
                    String nombreRestaurante = comentario.getRestaurante();

                    //Comprobar que el campo de la tabla Comemntarios coincida con el campo nombre Resataurante para solo añadir los comentarios que coincidan con el restaurante seleccionado.
                    if (nombre.equals(nombreRestaurante)) {
                        comentarios.add(comentario);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Obtención de la tabla usuario de Realtime Database
        database.getReference("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Obtención de la variable
                Bundle bundles = getIntent().getExtras();
                String email = bundles.getString("Email");

                //Vaciar el arrray
                usuarios.clear();

                //Recorrer toda la tabla usuario
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Usuario usuario = new Usuario();

                    usuario = snapshot.getValue(Usuario.class);
                    usuario.setIdUsuario(snapshot.getKey());

                    //Obtener el campo Correo de Realtime Database de la tabla usuario
                    String correo = usuario.getEmail();

                    //Comprobar que coincide el correo recibido de la variable con el correo de la tabla usuario para coger el usuario en el que estamos
                    if (correo.equals(email)) {
                        usuarios.add(usuario);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Volver a la actividad anterior
    public void goBack(View view) {
        onBackPressed();
    }

    //Cambiar ImaheButton para saber cual a sido seleccionada
    public void changeStar1(View v) {
        Btn1 = findViewById(R.id.puntuacion1);
        Btn2 = findViewById(R.id.puntuacion2);
        Btn3 = findViewById(R.id.puntuacion3);
        Btn4 = findViewById(R.id.puntuacion4);
        Btn5 = findViewById(R.id.puntuacion5);

        //Cambiar imagen
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_on_pressed);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_off_pressed);

        //Selecccionar la imagen que se va a poner
        Btn1.setImageBitmap(bmp);
        Btn2.setImageBitmap(bmp2);
        Btn3.setImageBitmap(bmp2);
        Btn4.setImageBitmap(bmp2);
        Btn5.setImageBitmap(bmp2);

        //Asociar valor a la puntuación
        puntuacion = "1";
    }

    //Cambiar ImaheButton para saber cual a sido seleccionada
    public void changeStar2(View v) {
        Btn1 = findViewById(R.id.puntuacion1);
        Btn2 = findViewById(R.id.puntuacion2);
        Btn3 = findViewById(R.id.puntuacion3);
        Btn4 = findViewById(R.id.puntuacion4);
        Btn5 = findViewById(R.id.puntuacion5);


        //Cambiar imagen
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_on_pressed);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_off_pressed);

        //Selecccionar la imagen que se va a poner
        Btn1.setImageBitmap(bmp);
        Btn2.setImageBitmap(bmp);
        Btn3.setImageBitmap(bmp2);
        Btn4.setImageBitmap(bmp2);
        Btn5.setImageBitmap(bmp2);

        //Asociar valor a la puntuación
        puntuacion = "2";

    }

    //Cambiar ImaheButton para saber cual a sido seleccionada
    public void changeStar3(View v) {
        Btn1 = findViewById(R.id.puntuacion1);
        Btn2 = findViewById(R.id.puntuacion2);
        Btn3 = findViewById(R.id.puntuacion3);
        Btn4 = findViewById(R.id.puntuacion4);
        Btn5 = findViewById(R.id.puntuacion5);

        //Cambiar imagen
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_on_pressed);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_off_pressed);

        //Selecccionar la imagen que se va a poner
        Btn1.setImageBitmap(bmp);
        Btn2.setImageBitmap(bmp);
        Btn3.setImageBitmap(bmp);
        Btn4.setImageBitmap(bmp2);
        Btn5.setImageBitmap(bmp2);

        //Asociar valor a la puntuación
        puntuacion = "3";

    }

    //Cambiar ImaheButton para saber cual a sido seleccionada
    public void changeStar4(View v) {
        Btn1 = findViewById(R.id.puntuacion1);
        Btn2 = findViewById(R.id.puntuacion2);
        Btn3 = findViewById(R.id.puntuacion3);
        Btn4 = findViewById(R.id.puntuacion4);
        Btn5 = findViewById(R.id.puntuacion5);

        //Cambiar imagen
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_on_pressed);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_off_pressed);

        //Selecccionar la imagen que se va a poner
        Btn1.setImageBitmap(bmp);
        Btn2.setImageBitmap(bmp);
        Btn3.setImageBitmap(bmp);
        Btn4.setImageBitmap(bmp);
        Btn5.setImageBitmap(bmp2);

        //Asociar valor a la puntuación
        puntuacion = "4";

    }

    //Cambiar ImaheButton para saber cual a sido seleccionada
    public void changeStar5(View v) {
        Btn1 = findViewById(R.id.puntuacion1);
        Btn2 = findViewById(R.id.puntuacion2);
        Btn3 = findViewById(R.id.puntuacion3);
        Btn4 = findViewById(R.id.puntuacion4);
        Btn5 = findViewById(R.id.puntuacion5);

        //Cambiar imagen
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_on_pressed);

        //Selecccionar la imagen que se va a poner
        Btn1.setImageBitmap(bmp);
        Btn2.setImageBitmap(bmp);
        Btn3.setImageBitmap(bmp);
        Btn4.setImageBitmap(bmp);
        Btn5.setImageBitmap(bmp);

        //Asociar valor a la puntuación
        puntuacion = "5";

    }


    //Añadir comentario y puntuacion a la base de datos
    public void añadirComent(View view) {
        TextView comenta;
        TextView precioMedioView;

        //Asociar el editText a la variable
        coment = findViewById(R.id.comentario);
        precio = findViewById(R.id.precioMedio);

        //Coger el Nombre del usuario con el que nos hemos logueado
        String nombreUsuario = usuarios.get(0).getNombreUsuario();

        //Obtención de la variable
        Bundle bundles = getIntent().getExtras();
        String nombrerestaurante = bundles.getString("nombre");

        //Asociar el texto recogido del editText a una variable, pasarlo a un String y sin espacios
        String comentario = coment.getText().toString().trim();
        String precioMedio = precio.getText().toString().trim();

        //Comprobar que los campos no estan vacios
        if (puntuacion.equals("")) {
            Toast.makeText(this, "Tienes que puntuar", Toast.LENGTH_SHORT).show();
        } else if (comentario.equals("")) {
                Toast.makeText(this, "Tienes que comentar", Toast.LENGTH_SHORT).show();

        } else {

            //Generar una id
            String id = databaseReference.push().getKey();

            //Rellenar el array
            Comentario rest = new Comentario(nombrerestaurante, nombreUsuario, puntuacion, comentario, precioMedio);

            //Agregar los datos a Realtime Database
            databaseReference.child("Comentarios").child(id).setValue(rest);

            ////Inicializar Cloud Firestore
            FirebaseFirestore dbs = FirebaseFirestore.getInstance();

            //Referenciar la tabla de la base de datos
            DocumentReference newComentarioRef = dbs.collection("Comentarios").document();

            //Creas un HasMap
            Map<String, Object> Comentario = new HashMap<>();

            //Rellenas el HasMap
            Comentario.put("Restaurante", nombrerestaurante);
            Comentario.put("Usuario", nombreUsuario);
            Comentario.put("Puntua", puntuacion);
            Comentario.put("Comenta", comentario);
            Comentario.put("precioMedio", precioMedio);

            //Agregas los datos de HasMap a la base de datos
            newComentarioRef.set(Comentario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                    } else {
                    }
                }
            });

            // Vaciar los editText
            Btn1 = findViewById(R.id.puntuacion1);
            Btn2 = findViewById(R.id.puntuacion2);
            Btn3 = findViewById(R.id.puntuacion3);
            Btn4 = findViewById(R.id.puntuacion4);
            Btn5 = findViewById(R.id.puntuacion5);
            comenta = findViewById(R.id.comentario);
            precioMedioView = findViewById(R.id.precioMedio);

            Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.btn_star_big_off_pressed);
            Btn1.setImageBitmap(bmp3);
            Btn2.setImageBitmap(bmp3);
            Btn3.setImageBitmap(bmp3);
            Btn4.setImageBitmap(bmp3);
            Btn5.setImageBitmap(bmp3);

            puntuacion = "";
            comenta.setText("");
            precioMedioView.setText("");
        }
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

                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
