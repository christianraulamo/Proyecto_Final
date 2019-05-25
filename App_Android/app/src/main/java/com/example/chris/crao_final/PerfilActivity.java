package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.crao_final.adapters.usuarioAdapter;
import com.example.chris.crao_final.modelos.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    DatabaseReference bbdd;
    private FirebaseDatabase database;
    usuarioAdapter adapter;
    List<Usuario> usuarios;
    int a = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Instanciar la tabla usuario de la base de datos
        bbdd = FirebaseDatabase.getInstance().getReference("usuario");

        //Instanciar la base de datos
        database = FirebaseDatabase.getInstance();

        //Recoger la variable
        Bundle bundles = getIntent().getExtras();
        final String Email = bundles.getString("Email");

        //Declarar los editText
        final TextView nombreUsuario, nombre, apellido;

        //Asociar los datos de los editText a variables
        nombreUsuario = findViewById(R.id.nombreDeUsuario);
        nombre = findViewById(R.id.Nombre);
        apellido = findViewById(R.id.Apellido);

        //Llamada a la tabla usuarios de la base de datos
        database.getReference("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Rcoger la variable
                Bundle bundles = getIntent().getExtras();
                String email = bundles.getString("Email");

                //Recorrer toda la tabla usuario
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Usuario usuario = new Usuario();

                    usuario = snapshot.getValue(Usuario.class);
                    usuario.setIdUsuario(snapshot.getKey());

                    //Obtener el campo Correo de Realtime Database de la tabla usuario
                    String correo = usuario.getEmail();
                    String nombreDeUsuario = usuario.getNombreUsuario();
                    String segundonombre = usuario.getApellidos();
                    String primernombre = usuario.getNombre();

                    //Comprobar que el correo no llegue vacio
                    if (correo != null) {

                        //Comprobar que el correo enviado y el de la base de datos sea igual
                        if (correo.equals(email)) {
                            //Coger la id del usuario y asociarlo a una variable
                            String clave = dataSnapshot.getKey();

                            //Asociar los datos de la base de datos al Hit de los editText
                            nombreUsuario.setHint(nombreDeUsuario);
                            nombre.setHint(primernombre);
                            apellido.setHint(segundonombre);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Modificar(View v) {

        //Declarar los editText
        final TextView nombreUsuario, nombre, apellido;

        //Asociar los datos recogidos de los editText a las variables
        nombreUsuario = findViewById(R.id.nombreDeUsuario);
        nombre = findViewById(R.id.Nombre);
        apellido = findViewById(R.id.Apellido);

        //Recoger la variable de la actividad anterior
        Bundle bundles = getIntent().getExtras();
        String email = bundles.getString("Email");

        //Hacer una Query del campo usuario de la base de datos que tenga el email igual al que hemos recibido
        Query q = bbdd.orderByChild(("email")).equalTo(email);

        //Llamar la base de datos de la tabla con la llamada de la Query
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Recorrer la base de datos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    //Asociar la id del usuario a una variable
                    String clave = datasnapshot.getKey();

                    Usuario usuario = new Usuario();

                    usuario = dataSnapshot.getValue(Usuario.class);
                    usuario.setIdUsuario(dataSnapshot.getKey());

                    //Asociar los datos recogidos de los ediText
                    String segundoNombre = apellido.getText().toString().trim();
                    String primerNombre = nombre.getText().toString().trim();
                    String usuario1 = nombreUsuario.getText().toString().trim();

                    adapter = new usuarioAdapter(this, R.layout.view_recycler, usuarios);

                    //Instanciar la base de datos
                    database = FirebaseDatabase.getInstance();

                    //Llamar a la base de datos de la tabla usuario
                    database.getReference("usuario").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            a = 0;
                            //Recorrer toda la tabla usuario
                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()) {

                                Usuario usuario = new Usuario();

                                usuario = snapshot.getValue(Usuario.class);
                                usuario.setIdUsuario(snapshot.getKey());

                                //Obtener el campo Correo de Realtime Database de la tabla usuario
                                String nombreDeUsuario = usuario.getNombreUsuario();

                                //Comprobar que coincide el nombre de usuario recibido de la variable con el nombre de usuario de la tabla usuario para coger el usuario en el que estamos
                                if (nombreDeUsuario.equals(nombreUsuario)) {
                                    a++;
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //Comprobar que el editText este vacio o no
                    if (usuario1.equals("")) {

                        //Asociar el valor obtenido de la Hit (base de datos) a la variable
                        usuario1 = nombreUsuario.getHint().toString();

                        //Cambiar el valor de la base de datos con el de la variable
                        bbdd.child(clave).child("nombreUsuario").setValue(usuario1);

                        //Comprobar que el editText este vacio o no
                        if (primerNombre.equals("")) {

                            //Asociar el valor obtenido de la Hit (base de datos) a la variable
                            primerNombre = nombre.getHint().toString();

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("nombre").setValue(primerNombre);
                        } else {

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("nombre").setValue(primerNombre);
                        }

                        //Comprobar que el editText este vacio o no
                        if (segundoNombre.equals("")) {

                            //Asociar el valor obtenido de la Hit (base de datos) a la variable
                            segundoNombre = apellido.getHint().toString();

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("apellidos").setValue(segundoNombre);
                        } else {

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("apellidos").setValue(segundoNombre);
                        }

                        //Mensaje para afirmar la actualización
                        Toast.makeText(PerfilActivity.this, "Se ha modificado con exito los datos.", Toast.LENGTH_LONG).show();

                        //Comprobación de si existe ya el nombre del usuario
                    } else if (a == 0) {

                        //Asociar el valor obtenido de la Hit (base de datos) a la variable
                        bbdd.child(clave).child("nombreUsuario").setValue(usuario1);

                        //Comprobar que el editText este vacio o no
                        if (primerNombre.equals("")) {

                            //Asociar el valor obtenido de la Hit (base de datos) a la variable
                            primerNombre = nombre.getHint().toString();

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("nombre").setValue(primerNombre);
                        } else {

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("nombre").setValue(primerNombre);
                        }

                        //Asociar el valor obtenido de la Hit (base de datos) a la variable
                        if (segundoNombre.equals("")) {

                            //Asociar el valor obtenido de la Hit (base de datos) a la variable
                            segundoNombre = apellido.getHint().toString();

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("apellidos").setValue(segundoNombre);
                        } else {

                            //Cambiar el valor de la base de datos con el de la variable
                            bbdd.child(clave).child("apellidos").setValue(segundoNombre);
                        }

                        //Mensaje de confirmación de la modificación
                        Toast.makeText(PerfilActivity.this, "Se ha modificado con exito los datos.", Toast.LENGTH_LONG).show();
                    } else {

                        //Mensaje de nombre usuario existente
                        Toast.makeText(PerfilActivity.this, "El nombre de usuario ya existe.", Toast.LENGTH_LONG).show();
                    }
                }
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
}
