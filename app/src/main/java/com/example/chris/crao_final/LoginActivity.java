package com.example.chris.crao_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText usr, pwd ;

    private FirebaseAuth mAuth;
    private FirebaseDatabase fbDB ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance() ;

    }

    public void acercade(View view) {
        Intent i = new Intent(this, RegisterActivity.class );
        startActivity(i);
    }

    public void loguea(final View v) {
        usr = findViewById(R.id.loginUser);
        pwd = findViewById(R.id.loginPassword);

        String usuario = usr.getText().toString().trim();
        String clave = pwd.getText().toString().trim();

        if (usuario.isEmpty() || clave.isEmpty()){
            Snackbar.make(v, R.string.login_error_vacio, Snackbar.LENGTH_LONG).show();

        }else {
            mAuth.signInWithEmailAndPassword(usuario,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
                        startActivity(intent) ;
                    } else {
                        Snackbar.make(v, R.string.login_error, Snackbar.LENGTH_LONG).show();
                    }
                }
            }) ;
        }
    }

    public void loguearte(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}
