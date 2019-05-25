package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText usr, pwd;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //Ocultar el teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().hide();


    }

    //Ir a la actividad RegisterActivity
    public void goToRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void login(final View v) {

        //Obtener datos de los editText
        usr = findViewById(R.id.loginUser);
        pwd = findViewById(R.id.loginPassword);

        //Transformar los datos de los editText a String y sin espacios
        String usuario = usr.getText().toString().trim();
        String clave = pwd.getText().toString().trim();

        //Comprobación de que los campos no esten vacios
        if (usuario.isEmpty() || clave.isEmpty()) {
            Snackbar.make(v, R.string.login_error_vacio, Snackbar.LENGTH_LONG).show();

        } else {
            //Comprobar que el correo exista en la base de datos y que coincida la contraseña
            mAuth.signInWithEmailAndPassword(usuario, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String Email = usr.getText().toString().trim();

                        //Enviar el correo que se ha introducido a la actividad MainActivity
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Email", Email);

                        //Ir a la actividad MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, R.string.login_error, Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    //Bloquear poder volver a actividad anterior
    @Override
    public void onBackPressed() {
    }

}
