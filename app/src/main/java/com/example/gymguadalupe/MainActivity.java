package com.example.gymguadalupe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnIniciarsesion,btnHomeMembresiasPage,btnQuienesSomos,btnContactanosPage,btnUbicacionPage,btnMemPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnIniciarsesion = findViewById(R.id.btnIniciarsesion);
        btnIniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                startActivity(intent);

            }
        });


        btnQuienesSomos = findViewById(R.id.btnQuienesSomos);
        btnQuienesSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });

        btnContactanosPage = findViewById(R.id.btnContactanosPage);
        btnContactanosPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ContactanosActivity.class);
                startActivity(intent);
            }
        });

        btnUbicacionPage = findViewById(R.id.btnUbicacionPage);
        btnUbicacionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        btnMemPage= findViewById(R.id.btnMemPage);
        btnMemPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoMembresiasActivity.class);
                startActivity(intent);
            }
        });

    }
}