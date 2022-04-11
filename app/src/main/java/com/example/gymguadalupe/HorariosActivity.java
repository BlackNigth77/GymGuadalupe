package com.example.gymguadalupe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class HorariosActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText horarioEditText, turnoEditText  ;
    Button btnNuevoHorario;
    String horario,turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        inicializarFirebase();
        asignarReferencias();
    }

    private void asignarReferencias() {
        horarioEditText = findViewById(R.id.horarioEditText);
        turnoEditText = findViewById(R.id.turnoEditText);
        btnNuevoHorario = findViewById(R.id.btnNuevoHorario);
        btnNuevoHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHorarios();
                finish();
            }
        });
    }

    private void registrarHorarios() {
        horario = horarioEditText.getText().toString();
        turno = turnoEditText.getText().toString();
        if (horario.equals("") || turno.equals("")){
            mostrarErrores();
        }else{
            Horario h= new Horario();
            h.setId_horariro(UUID.randomUUID().toString());
            h.setHorario(horario);
            h.setTurno(turno);
            databaseReference.child("Horario").child(h.getId_horariro()).setValue(h);
            Toast.makeText(this,"Registro correcto",Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarErrores() {
        if(horario.contentEquals("")){
            horarioEditText.setError("Requerido");
        }
        if(turno.equals("")){
            turnoEditText.setError("Requerido");
        }
    }

    private void inicializarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}