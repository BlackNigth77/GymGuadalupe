package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class MembresiaActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    TextView txtusuarioM,idmembresia;
    EditText nombreMEditText, tipoMEditText,costoEMditText  ;
    ImageButton btnAddMembresia,btnEditarMembresia,btnEliminarMembresia,btnBusqueda,btnClear;
    String membresia,tipo,costo, usuario, id,criterio,idmem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        inicializarFirebase();
        asignarReferencias();
        getUserName();
    }

    private void asignarReferencias() {
        nombreMEditText = findViewById(R.id.nombreMEditText);
        tipoMEditText = findViewById(R.id.tipoMEditText);
        costoEMditText = findViewById(R.id.costoEMditText);
        txtusuarioM = findViewById(R.id.txtusuarioM);
        idmembresia = findViewById(R.id.idmembresia);
        btnEditarMembresia = findViewById(R.id.btnEditarMembresia);
        btnEliminarMembresia = findViewById(R.id.btnEliminarMembresia);
        btnAddMembresia = findViewById(R.id.btnAddMembresia);
        btnBusqueda = findViewById(R.id.btnBusqueda);
        btnClear = findViewById(R.id.btnClear);

        btnEditarMembresia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        btnAddMembresia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarM();
            }
        });

        btnEliminarMembresia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                nombreMEditText.setText("");
                tipoMEditText.setText("");
                costoEMditText.setText("");
                idmembresia.setText("");
            }
        });

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busquedaEdit();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreMEditText.setText("");
                tipoMEditText.setText("");
                costoEMditText.setText("");
                idmembresia.setText("");
            }
        });
    }



    private void eliminar() {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        idmem = idmembresia.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("Desea elimar este registro");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child("Membresias").child(idmem).removeValue();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }



    private void registrarM() {
        membresia = nombreMEditText.getText().toString();
        tipo = tipoMEditText.getText().toString();
        costo = costoEMditText.getText().toString();
        if (membresia.equals("") || tipo.equals("") || costo.equals("")){
            mostrarErrores();
        }else{
            Membresias m= new Membresias();
            m.setId_membresia(UUID.randomUUID().toString());
            m.setNombre_membresia(membresia);
            m.setTipo_membresia(tipo);
            m.setCosto_mebresia(costo);
            databaseReference.child("Membresias").child(m.getId_membresia()).setValue(m);
            Toast.makeText(this,"Se registro la membresia",Toast.LENGTH_SHORT).show();
        }
    }


    private void actualizar() {
        id = idmembresia.getText().toString();
        membresia = nombreMEditText.getText().toString();
        tipo = tipoMEditText.getText().toString();
        costo = costoEMditText.getText().toString();

            HashMap map = new HashMap();
            map.put("costo_mebresia",costo);
            map.put("nombre_membresia",membresia);
            map.put("tipo_membresia",tipo);
            databaseReference.child("Membresias").child(id).updateChildren(map);
            Toast.makeText(this,"Se actualizo correctamente",Toast.LENGTH_SHORT).show();
    }


    private void busquedaEdit() {
        criterio = nombreMEditText.getText().toString();
        Query query = mDatabase.child("Membresias").orderByChild("nombre_membresia").equalTo(criterio);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot socios : datasnapshot.getChildren()) {
                    tipoMEditText.setText(socios.child("tipo_membresia").getValue().toString());
                    costoEMditText.setText(socios.child("costo_mebresia").getValue().toString());
                    idmembresia.setText(socios.child("id_membresia").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void mostrarErrores() {
        if(membresia.equals("")){
            nombreMEditText.setError("Requerido");
        }
        if(tipo.equals("")){
            tipoMEditText.setError("Requerido");
        }
        if(costo.equals("")){
            costoEMditText.setError("Requerido");
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
                    txtusuarioM.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}