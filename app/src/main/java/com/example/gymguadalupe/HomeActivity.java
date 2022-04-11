package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView emailTexView, nombresocio,apellidossocio,celularsocio,emailsocio,fnsocio,idsocio;
    TextView txtidpago,txtcostopago,txtfecpago,txtfecvenc,txtmempago;
    TextView txtespaldaobj,txtcaderaobj,txtpesoobj,txtimcobj,txtcinturaobj,txtfechaobj,txtidobjetivo;

    Button btnCerrarsesion,btnDatosPersonalesPage,btnMembresiasPage,bntObjetivosPage,btnReservasPage;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String criterio,id_pagos,id_socioPage,id_obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        emailTexView = findViewById(R.id.emailTextView);
        nombresocio = findViewById(R.id.nombresocio);
        apellidossocio = findViewById(R.id.apellidossocio);
        celularsocio = findViewById(R.id.celularsocio);
        emailsocio = findViewById(R.id.emailsocio);
        fnsocio = findViewById(R.id.fnsocio);
        idsocio = findViewById(R.id.idsocio);

        txtidpago = findViewById(R.id.txtidpago);
        txtcostopago = findViewById(R.id.txtcostopago);
        txtfecpago = findViewById(R.id.txtfecpago);
        txtfecvenc = findViewById(R.id.txtfecvenc);
        txtmempago = findViewById(R.id.txtmempago);

        txtespaldaobj = findViewById(R.id.txtespaldaobj);
        txtcaderaobj= findViewById(R.id.txtcaderaobj);
        txtpesoobj= findViewById(R.id.txtpesoobj);
        txtimcobj= findViewById(R.id.txtimcobj);
        txtcinturaobj= findViewById(R.id.txtcinturaobj);
        txtfechaobj= findViewById(R.id.txtfechaobj);
        txtidobjetivo = findViewById(R.id.txtidobjetivo);

        btnReservasPage = findViewById(R.id.btnReservasPage);
        bntObjetivosPage = findViewById(R.id.bntObjetivosPage);
        btnMembresiasPage = findViewById(R.id.btnMembresiasPage);
        btnDatosPersonalesPage = findViewById(R.id.btnDatosPersonalesPage);
        btnCerrarsesion = findViewById(R.id.btnCerrarsesion);
        getUserName();
        emailTexView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                busquedaSocio();
                busquedaPago();
                busquedaObjetivo();
            }
        });



        btnCerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this,AuthActivity.class));
                finish();

            }
        });
        btnDatosPersonalesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_socioPage = idsocio.getText().toString();
                if (!id_socioPage.isEmpty())
                {

                Intent intent = new Intent(HomeActivity.this,SocioActivity.class);
                intent.putExtra("nombres_socios",nombresocio.getText().toString());
                intent.putExtra("apellidos_socios",apellidossocio.getText().toString());
                intent.putExtra("celular_socios",celularsocio.getText().toString());
                intent.putExtra("email_socios",emailsocio.getText().toString());
                intent.putExtra("fnsocio_socios",fnsocio.getText().toString());
                intent.putExtra("id_socios",idsocio.getText().toString());
                startActivity(intent);

                }else {
                    Toast.makeText(HomeActivity.this, "Usted no tiene registrado Datos Personales", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this,RegistrarSocioActivity.class));
                }

            }
        });

        btnMembresiasPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_pagos = txtidpago.getText().toString();

                if (!id_pagos.isEmpty())
                {
                    Intent intent = new Intent(HomeActivity.this,MembresiaVigenteActivity.class);
                    intent.putExtra("nompago",txtmempago.getText().toString());
                    intent.putExtra("fecpago",txtfecpago.getText().toString());
                    intent.putExtra("fecpagoven",txtfecvenc.getText().toString());
                    intent.putExtra("costopago",txtcostopago.getText().toString());
                    intent.putExtra("idpago",txtidpago.getText().toString());
                    startActivity(intent);

                }else {
                    Toast.makeText(HomeActivity.this, "Usted no tiene membresia registrada", Toast.LENGTH_SHORT).show();

                }
            }
        });

        bntObjetivosPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id_obj = txtidobjetivo.getText().toString();

                if (!id_obj.isEmpty())
                {
                    Intent intent = new Intent(HomeActivity.this,OjetivosActivity.class);
                    intent.putExtra("espalda",txtespaldaobj.getText().toString());
                    intent.putExtra("cadera",txtcaderaobj.getText().toString());
                    intent.putExtra("peso",txtpesoobj.getText().toString());
                    intent.putExtra("IMC",txtimcobj.getText().toString());
                    intent.putExtra("cintura",txtcinturaobj.getText().toString());
                    intent.putExtra("fecha",txtfechaobj.getText().toString());
                    intent.putExtra("id",txtidobjetivo.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(HomeActivity.this, "Usted no tiene Objetivos registrados", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this,ListadoObjetivosActivity.class));
                }

            }
        });

        btnReservasPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ReservasActivity.class));
            }
        });

    }

    private void busquedaSocio() {
        criterio = emailTexView.getText().toString();
        Query query = mDatabase.child("Socio").orderByChild("direccionSocio").equalTo(criterio);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot socios : datasnapshot.getChildren()) {
                    nombresocio.setText(socios.child("nombreSocio").getValue().toString());
                    apellidossocio.setText(socios.child("apellidosSocio").getValue().toString());
                    celularsocio.setText(socios.child("celularSocio").getValue().toString());
                    emailsocio.setText(socios.child("direccionSocio").getValue().toString());
                    fnsocio.setText(socios.child("fnsocio").getValue().toString());
                    idsocio.setText(socios.child("id_socio").getValue().toString());
                                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void busquedaPago() {
        criterio = emailTexView.getText().toString();
        Query query = mDatabase.child("Pagos").orderByChild("email_pago").equalTo(criterio);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot pagos : datasnapshot.getChildren()) {
                    txtcostopago.setText(pagos.child("costo_pago").getValue().toString());
                    txtfecvenc.setText(pagos.child("fecha_vencimiento_pago").getValue().toString());
                    txtfecpago.setText(pagos.child("fecha_registro_pago").getValue().toString());
                    txtmempago.setText(pagos.child("membresia_pago").getValue().toString());
                    txtidpago.setText(pagos.child("id_pago").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void busquedaObjetivo() {
        criterio = emailTexView.getText().toString();
        Query query = mDatabase.child("Objetivos").orderByChild("user_objetivo").equalTo(criterio);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot objetivos : datasnapshot.getChildren()) {
                    txtespaldaobj.setText(objetivos.child("espalda_objetivo").getValue().toString());
                    txtcaderaobj.setText(objetivos.child("cadera_objetivo").getValue().toString());
                    txtpesoobj.setText(objetivos.child("peso_objetivo").getValue().toString());
                    txtimcobj.setText(objetivos.child("imc_objetivo").getValue().toString());
                    txtcinturaobj.setText(objetivos.child("cintura_objetivo").getValue().toString());
                    txtfechaobj.setText(objetivos.child("fecha_registro_objetivo").getValue().toString());
                    txtidobjetivo.setText(objetivos.child("id_objetivo").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void getUserName(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    String email = datasnapshot.child("email").getValue().toString();
                    emailTexView.setText(email);
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

}