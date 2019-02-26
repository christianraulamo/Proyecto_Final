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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chris.crao_final.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nom, ape, ema ;
    private EditText pwd1, pwd2 ;

    private Button registerBtn ;
    private FirebaseAuth mAuth ;
    private FirebaseDatabase db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom  = findViewById(R.id.nombre) ;
        ape  = findViewById(R.id.apellidos) ;
        ema  = findViewById(R.id.correo) ;
        pwd1 = findViewById(R.id.contraseña1) ;
        pwd2 = findViewById(R.id.contraseña2) ;

        registerBtn = findViewById(R.id.registrar) ;

        InputFilter alfaFilter = new InputFilter() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for(int i=start; i<end; i++) {
                    if (!Character.isAlphabetic(source.charAt(i))) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphabetic,
                                Toast.LENGTH_SHORT).show();
                        return "" ;
                    }
                }

                return null;
            }
        } ;

        // Aceptamos caracteres alfanuméricos
        InputFilter alfanumFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source,
                                       int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for(int i=start; i<end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        Toast.makeText(RegisterActivity.this,
                                R.string.register_error_alphanumeric,
                                Toast.LENGTH_SHORT).show();
                        return "" ;
                    }
                }

                return null;
            }
        } ;

        //
        // Asociamos los filtros
        nom.setFilters(new InputFilter[] { alfaFilter } );
        ape.setFilters(new InputFilter[] { alfaFilter });
        //usu.setFilters(new InputFilter[] { alfanumFilter,
        //                                   new InputFilter.LengthFilter(15) } );

        pwd1.setFilters(new InputFilter[] {  new InputFilter.LengthFilter(15) } );
        pwd2.setFilters(new InputFilter[] {  new InputFilter.LengthFilter(15) } );


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                // Obtenemos los valores introducidos por el usuario
                final String usuario = ema.getText().toString().trim() ;
                final String nombre = nom.getText().toString().trim() ;
                final String apellido = ape.getText().toString().trim() ;
                final String clave   = pwd1.getText().toString().trim() ;
                final String clave2  = pwd2.getText().toString().trim() ;

                // Obtenemos una instancia del objeto FirebaseAuth
                mAuth = FirebaseAuth.getInstance() ;
                mAuth.createUserWithEmailAndPassword(usuario,clave)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(usuario.isEmpty() || clave.isEmpty() || clave2.isEmpty() || apellido.isEmpty() || nombre.isEmpty()) {
                                    Snackbar.make(v, R.string.login_error_vacio, Snackbar.LENGTH_LONG).show();

                                }else if(!clave.equals(clave2)){
                                    Snackbar.make(v, R.string.Register_error_contraseña, Snackbar.LENGTH_LONG).show();
                                }else if (task.isSuccessful()) {
                                    // Si, nuestro usuario tiene más información asociada,
                                    // tendremos que guardarla en la base de datos.

                                    // Obtenemos una instancia de la base de datos
                                    db = FirebaseDatabase.getInstance();

                                    // Obtenemos una referencia al documento (tabla) que contendrá
                                    // la información. Si el documento no existe, Firebase lo crea.
                                    DatabaseReference ref = db.getReference("usuario");

                                    // Obtener los datos proporcionados por Firebase sobre el usuario
                                    // registrado.
                                    FirebaseUser fbUser = mAuth.getCurrentUser();

                                    // Preguntamos por el UID
                                    String uid = fbUser.getUid();

                                    // Creamos nuestro objeto usuario con los datos proporcionados
                                    // a través del formulario.
                                    Usuario nuevoUsuario = new Usuario(uid,
                                            nom.getText().toString(),
                                            ape.getText().toString(),
                                            ema.getText().toString());

                                                    // Guardamos la información en la base de datos de Firebase,
                                                    // asociados al UID.
                                                    // ref.child(uid).setValue(miUsuario);

                                    ref.child(uid).setValue(nuevoUsuario);

                                    Toast.makeText(RegisterActivity.this, "Se ha creado con éxito.", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class) ;
                                    startActivity(intent) ;

                                } else {
                                    Toast.makeText(RegisterActivity.this, "Se ha producido un error en el registro.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        }) ;
    }
}
