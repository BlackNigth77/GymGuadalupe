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

import java.util.UUID;

public class RegistrarSocioActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText nSocioEditText,aSocioEditText,cSocioEditText,eSocioEditText,fn_SocioEditText;
    TextView txtuserSocio;
    String nSocio,aSocio,cSocio,eSocio,fn_Socio,id;
    Button btnAddSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_socio);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nSocioEditText = findViewById(R.id.nSocioEditText);
        aSocioEditText = findViewById(R.id.aSocioEditText);
        cSocioEditText = findViewById(R.id.cSocioEditText);
        eSocioEditText = findViewById(R.id.eSocioEditText);
        txtuserSocio = findViewById(R.id.txtuserSocio);
        fn_SocioEditText = findViewById(R.id.fn_SocioEditText);
        inicializarFirebase();
        getUserName();
        btnAddSocio = findViewById(R.id.btnAddSocio);
        btnAddSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarSocio();
                finish();
            }
        });

    }

    private void registrarSocio() {
        nSocio = nSocioEditText.getText().toString();
        aSocio = aSocioEditText.getText().toString();
        cSocio = cSocioEditText.getText().toString();
        eSocio = txtuserSocio.getText().toString();
        fn_Socio = cSocioEditText.getText().toString();

        Socio s = new Socio();
        s.setId_socio(UUID.randomUUID().toString());
        s.setNombreSocio(nSocio);
        s.setApellidosSocio(aSocio);
        s.setCelularSocio(cSocio);
        s.setDireccionSocio(eSocio);
        s.setFNSocio(fn_Socio);
        databaseReference.child("Socio").child(s.getId_socio()).setValue(s);
        Toast.makeText(this,"Se registro los datos exitosamente",Toast.LENGTH_SHORT).show();
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
                    txtuserSocio.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}