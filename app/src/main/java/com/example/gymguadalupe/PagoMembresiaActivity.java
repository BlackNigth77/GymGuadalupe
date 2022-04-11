package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PagoMembresiaActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    TextView txtUsuarioPago,txtFechaPago,txtFechaVenc ;
    EditText nombrePEditText,precioPEditText;
    String usuarioPago,fechaPago,fechavenc,nombreMemPago,precioPago;
    Button btnAddPago;

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String datevenc = "2021-07-30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_membresia);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtUsuarioPago = findViewById(R.id.txtUsuarioMem);
        txtFechaPago = findViewById(R.id.txtFechaMem);
        txtFechaVenc = findViewById(R.id.txtFechaVencMem);
        precioPEditText = findViewById(R.id.txtprecioMem);
        nombrePEditText = findViewById(R.id.txtNombreMem);
        inicializarFirebase();
        getUserName();
        txtFechaVenc.setText(datevenc);
        txtFechaPago.setText(date);
        btnAddPago = findViewById(R.id.btnAddPago);
        btnAddPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPago();
            }
        });

    }


    private void registrarPago() {
        usuarioPago = txtUsuarioPago.getText().toString();
        fechaPago = txtFechaPago.getText().toString();
        fechavenc = txtFechaVenc.getText().toString();
        nombreMemPago = nombrePEditText.getText().toString();
        precioPago = precioPEditText.getText().toString();
        if (usuarioPago.equals("") || fechaPago.equals("") || fechavenc.equals("") || nombreMemPago.equals("") || precioPago.equals("")){
            mostrarErrores();
        }else{
            Pagos p= new Pagos();
            p.setId_pago(UUID.randomUUID().toString());
            p.setFecha_registro_pago(fechaPago);
            p.setFecha_vencimiento_pago(fechavenc);
            p.setMembresia_pago(nombreMemPago);
            p.setEmail_pago(usuarioPago);
            p.setCosto_pago(precioPago);
            databaseReference.child("Pagos").child(p.getId_pago()).setValue(p);
            Toast.makeText(this,"Registro correcto",Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarErrores() {
        if(usuarioPago.contentEquals("")){
            txtUsuarioPago.setError("Requerido");
        }
        if(fechaPago.equals("")){
            txtFechaPago.setError("Requerido");
        }
        if(fechavenc.equals("")){
            txtFechaVenc.setError("Requerido");
        }
        if(nombreMemPago.equals("")){
            nombrePEditText.setError("Requerido");
        }
        if(precioPago.equals("")){
            precioPEditText.setError("Requerido");
        }
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
                    txtUsuarioPago.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}