package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListadoObjetivosActivity extends AppCompatActivity {
    RecyclerView recyclerObjetivos;
    FloatingActionButton btnAgregarObjetivos;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView txt_usurio_objetivo;
    String criterio;
    //Crear una instancia de Firebase para las transacciones
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Objetivos> listaObjetivos = new ArrayList<>();
    AdaptadorPersonalizado adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_objetivos);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerObjetivos = findViewById(R.id.recyclerObjetivos);
        txt_usurio_objetivo = findViewById(R.id.txt_usurio_objetivo);
        asignarReferencias();
        inicializarFirebase();
        getUserName();

        txt_usurio_objetivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ListarDatos();
            }
        });

    }

    private void ListarDatos() {
        criterio = txt_usurio_objetivo.getText().toString();
        Query query = mDatabase.child("Objetivos").orderByChild("user_objetivo").equalTo(criterio);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listaObjetivos.clear();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Objetivos o = item.getValue(Objetivos.class);
                        listaObjetivos.add(o);
                    }
                    adaptador = new AdaptadorPersonalizado(ListadoObjetivosActivity.this, listaObjetivos);
                    recyclerObjetivos.setAdapter(adaptador);
                    recyclerObjetivos.setLayoutManager(new LinearLayoutManager(ListadoObjetivosActivity.this));
                }
                else {
                    Toast.makeText(ListadoObjetivosActivity.this, "Usted no tiene registrado objetivos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void asignarReferencias() {

        btnAgregarObjetivos = findViewById(R.id.btnAgregarObjetivos);
        btnAgregarObjetivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Para abrir en el boton + el nuevo activity Registar
                Intent intent = new Intent(ListadoObjetivosActivity.this,OjetivosActivity.class);
                startActivity(intent);
                //

            }
        });
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
                    txt_usurio_objetivo.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}