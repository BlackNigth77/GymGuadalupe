package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReservasActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView txt_usuario, txt_fecha_hoy, txtvalor;
    String usuario,fecha,horario;
    CheckBox turnoMcheckBox,turnoTcheckBox,turnoNcheckBox;
    ImageButton btnAddReserva,btnAddReserva1,btnAddReserva2;
    String valor;
    String datetoday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txt_usuario = findViewById(R.id.txt_usuario);
        txt_fecha_hoy = findViewById(R.id.txt_fecha_hoy);
        txt_fecha_hoy.setText(datetoday);
        turnoMcheckBox = findViewById(R.id.turnoMcheckBox);
        turnoTcheckBox = findViewById(R.id.turnoTcheckBox);
        turnoNcheckBox = findViewById(R.id.turnoNcheckBox);
        txtvalor = findViewById(R.id.txtvalor);
        inicializarFirebase();
        getUserName();

        turnoMcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoTcheckBox.setChecked(false);
                turnoNcheckBox.setChecked(false);
            }
        });

        turnoTcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoMcheckBox.setChecked(false);
                turnoNcheckBox.setChecked(false);
            }
        });

        turnoNcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoMcheckBox.setChecked(false);
                turnoTcheckBox.setChecked(false);
            }
        });


        btnAddReserva = findViewById(R.id.btnAddReserva);
        btnAddReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorHorario();
                registrarReserva();
                finish();
            }
        });

        btnAddReserva1 = findViewById(R.id.btnAddReserva1);
        btnAddReserva1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorHorario();
                registrarReserva();
                finish();
            }
        });

        btnAddReserva2 = findViewById(R.id.btnAddReserva2);
        btnAddReserva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorHorario();
                registrarReserva();
                finish();
            }
        });

    }

    private void valorHorario(){
        if (turnoMcheckBox.isChecked() == true){
            valor = turnoMcheckBox.getText().toString();
            txtvalor.setText(valor);
        }if  (turnoTcheckBox.isChecked() == true){
            valor = turnoTcheckBox.getText().toString();
            txtvalor.setText(valor);
        }
        if  (turnoNcheckBox.isChecked() == true){
            valor = turnoNcheckBox.getText().toString();
            txtvalor.setText(valor);
        }
    }

   private void registrarReserva() {
        usuario = txt_usuario.getText().toString();
        fecha = txt_fecha_hoy.getText().toString();
        horario = txtvalor.getText().toString();

            Reserva r= new Reserva();
            r.setId_reserva(UUID.randomUUID().toString());
            r.setEmail_reserva(usuario);
            r.setFecha_reserva(fecha);
            r.setHorario_reserva(horario);
            databaseReference.child("Reserva").child(r.getId_reserva()).setValue(r);
            Toast.makeText(this,"Se registro el horario exitosamente",Toast.LENGTH_SHORT).show();
    }

    private void inicializarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }



    private void getUserName(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    String email = datasnapshot.child("email").getValue().toString();
                    txt_usuario.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}