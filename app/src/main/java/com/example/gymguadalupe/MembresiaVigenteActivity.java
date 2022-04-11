package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MembresiaVigenteActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    TextView txtFechaMem,txtFechaVencMem,txtNombreMem,txtprecioMem,txtUsuarioMem;
    String txtFecha,txtFechaVenc,txtNom,txtprecio,idpagosM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia_vigente);
        inicializarFirebase();
        asignarReferencias();
    }

    private void asignarReferencias() {
    mAuth = FirebaseAuth.getInstance();
    mDatabase = FirebaseDatabase.getInstance().getReference();
    getUserName();
        txtFechaMem = findViewById(R.id.txtFechaMem);
        txtFechaVencMem = findViewById(R.id.txtFechaVencMem);
        txtNombreMem = findViewById(R.id.txtNombreMem);
        txtprecioMem = findViewById(R.id.txtprecioMem);
        txtUsuarioMem = findViewById(R.id.txtUsuarioMem);

        Bundle dataM = new Bundle();
        dataM = getIntent().getExtras();
        idpagosM = dataM.getString("idpago");
        txtFecha = dataM.getString("fecpago");
        txtFechaMem.setText(txtFecha);
        txtFechaVenc = dataM.getString("fecpagoven");
        txtFechaVencMem.setText(txtFechaVenc);
        txtNom = dataM.getString("nompago");
        txtNombreMem.setText(txtNom);
        txtprecio= dataM.getString("costopago");
        txtprecioMem.setText(txtprecio);
    }

    private void inicializarFirebase() {
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
                    txtUsuarioMem.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}