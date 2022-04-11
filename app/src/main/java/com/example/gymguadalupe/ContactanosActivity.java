package com.example.gymguadalupe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactanosActivity extends AppCompatActivity {
    Button btnEnviarDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactanos);
        btnEnviarDatos = findViewById(R.id.btnEnviarDatos);
        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactanosActivity.this, "Se envio su informacin no estaremos comunicando con usted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}