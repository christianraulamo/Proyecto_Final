package com.example.chris.crao_final;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InfoMainActivity extends AppCompatActivity {

    private TextView nom, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_main);

        Bundle bundle = getIntent().getExtras() ;
        String telefono = bundle.getString("telefono") ;

        tel = findViewById(R.id.textTelephone) ;
        tel.setText(telefono) ;
    }

    public void mapa (View v){
        Bundle bundle = getIntent().getExtras() ;
        String ubicacion = bundle.getString("ubicacion") ;

        Intent location = new Intent(Intent.ACTION_VIEW);
        Uri geo = Uri.parse(ubicacion);
        location.setData(geo);
        startActivity(location);

    }

    public void llamar (View v){

        Bundle bundle = getIntent().getExtras() ;
        String telefono = bundle.getString("telefono") ;

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.putExtra(Intent.ACTION_CALL, new String[]{telefono});
        call.setData(Uri.parse("tel:" + telefono));

        startActivity(call);

    }
}
