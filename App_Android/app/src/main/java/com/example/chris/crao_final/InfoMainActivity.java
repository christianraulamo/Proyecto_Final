package com.example.chris.crao_final;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InfoMainActivity extends AppCompatActivity {

    private TextView nom, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_main);

        //Obtenemos la variable dl numero de telefono de la actividad InfoActivity
        Bundle bundle = getIntent().getExtras();
        String telefono = bundle.getString("telefono");

        //Con la variable obtenida la insertamos en la View
        tel = findViewById(R.id.textTelephone);
        tel.setText(telefono);
    }

    public void map(View v) {
        //Obtenemos la variable de la ubicación de la actividad InfoActivity
        Bundle bundle = getIntent().getExtras();
        String ubicacion = bundle.getString("ubicacion");

        // Abrir y buscar en google map respecto a la variable anterior
        Intent location = new Intent(Intent.ACTION_VIEW);
        Uri geo = Uri.parse(ubicacion);
        location.setData(geo);
        startActivity(location);

    }

    public void call(View v) {
        //Obtenemos la variable dl numero de telefono de la actividad InfoActivity
        Bundle bundle = getIntent().getExtras();
        String telefono = bundle.getString("telefono");

        // Abrir aplicación telefono y rellenarlo con el numero de telefono
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.putExtra(Intent.ACTION_CALL, new String[]{telefono});
        call.setData(Uri.parse("tel:" + telefono));

        startActivity(call);

    }

    // Volver a actividad anterior
    public void goBack(View view) {
        onBackPressed();
    }


}
