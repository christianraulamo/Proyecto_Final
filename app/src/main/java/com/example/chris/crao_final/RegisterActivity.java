package com.example.chris.crao_final;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chris.crao_final.adapters.usuarioAdapter;
import com.example.chris.crao_final.modelos.Comentario;
import com.example.chris.crao_final.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private EditText nom, ape, ema, nomUsu;
    private EditText pwd1, pwd2;
    public int a = 1;

    private Button registerBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private FirebaseFirestore db2;

    usuarioAdapter adapter;
    List<Usuario> usuarios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Ocultar el teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().hide();

    }

    public void register(View v) {

        nom = findViewById(R.id.nombre);
        ape = findViewById(R.id.apellidos);
        nomUsu = findViewById(R.id.nombreUsuario);
        ema = findViewById(R.id.correo);
        pwd1 = findViewById(R.id.contraseña1);
        pwd2 = findViewById(R.id.contraseña2);

        registerBtn = findViewById(R.id.registrar);

        InputFilter alfaFilter = new InputFilter() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isAlphabetic(source.charAt(i))) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphabetic,
                                Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }

                return null;
            }
        };

        // Aceptamos caracteres alfanuméricos
        InputFilter alfanumFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphanumeric,
                                Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }

                return null;
            }
        };

        nom.setFilters(new InputFilter[]{alfaFilter});
        ape.setFilters(new InputFilter[]{alfaFilter});

        pwd1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        pwd2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        final String usuario = ema.getText().toString().trim();
        final String nombre = nom.getText().toString().trim();
        final String apellido = ape.getText().toString().trim();
        final String nombreUsuario = nomUsu.getText().toString().trim();
        final String clave = pwd1.getText().toString().trim();
        final String clave2 = pwd2.getText().toString().trim();



        //Comprobación de los campos no esten vacios
        if (usuario == null && findViewById(R.id.apellidos) == null && nombre == null && apellido == null && clave == null && clave2 == null && nombreUsuario == null) {
            Snackbar.make(v, R.string.login_error_vacio, Snackbar.LENGTH_LONG).show();
            //Comprobación de las claves
        } else if (!clave.equals(clave2)) {
            Snackbar.make(v, R.string.Register_error_contraseña, Snackbar.LENGTH_LONG).show();
        } else {

            adapter = new usuarioAdapter(this, R.layout.view_recycler, usuarios);
            db = FirebaseDatabase.getInstance();

            db.getReference("usuario").addValueEventListener(new ValueEventListener() {
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
        }

        // comprobar que no exista ningun usuario con ese nombre
        if (a == 0) {
                Log.d("prueba2", String.valueOf(nombreUsuario));
                //Instanciar Realtime Database
                mAuth = FirebaseAuth.getInstance();
                //Crear nuevo Usuario
                mAuth.createUserWithEmailAndPassword(usuario, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Comprobación de la creación de usuario correctamente
                        if (task.isSuccessful()) {
                            //Instanciar Realtime Database
                            db = FirebaseDatabase.getInstance();

                            //Indicar la tabla en la que se van a añadir los campos
                            DatabaseReference ref = db.getReference("usuario");

                            //Creación de una uid
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            String uid = fbUser.getUid();

                            //rellenar el objeto
                            Usuario nuevoUsuario = new Usuario(uid,
                                    nom.getText().toString(),
                                    ape.getText().toString(),
                                    ema.getText().toString(),
                                    nomUsu.getText().toString());

                            //Añadir el objeto a Realtime Database
                            ref.child(uid).setValue(nuevoUsuario);


                            Toast.makeText(RegisterActivity.this, "Se ha creado con éxito.", Toast.LENGTH_LONG).show();

                            //Inicializar Cloud Firestore
                            FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                            //Referenciar la tabla de la base de datos
                            DocumentReference newUsuarioRef = db2.collection("usuario").document();

                            //Crear un objeto nuevo
                            Usuario usuario = new Usuario();

                            //Rellenar el objeto
                            usuario.setIdUsuario(uid);
                            usuario.setEmail(ema.getText().toString());
                            usuario.setApellidos(ape.getText().toString());
                            usuario.setApellidos(nomUsu.getText().toString());
                            usuario.setNombre(nom.getText().toString());

                            //Meter el objeto en la base de datos
                            newUsuarioRef.set(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                    } else {
                                    }
                                }
                            });

                            //Cambiar de actividad
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterActivity.this, "Se ha producido un error en el registro.", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            } else {
                Toast.makeText(RegisterActivity.this, "El usuario ya existe.", Toast.LENGTH_LONG).show();
            }
    }

    //Volver a la actividad anterior
    public void goBack(View view) {
        onBackPressed();
    }

}

