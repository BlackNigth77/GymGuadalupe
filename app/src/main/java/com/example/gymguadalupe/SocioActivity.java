package com.example.gymguadalupe;

import androidx.annotation.NonNull;

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

import java.util.HashMap;

public class SocioActivity extends HomeActivity {
    //AppCompatActivity
    //Instancia de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    EditText NombreSocioEditText,ApellidosSocioEditText,CelularSocioEditText,DireccionSocioEditText,FNSocioEditText;

    Button btnActualizarSocio;
    TextView txtuser;
    String NombreSocio,ApellidosSocio, CelularSocio,DireccionSocio,FNSocio,nsocio,asocio,csocio,esocio,fsocio,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);
        inicializarFirebase();
        asignarReferencias();
        btnActualizarSocio = findViewById(R.id.btnAddSocio);
        btnActualizarSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarSocio();
            }
        });
    }


    private void asignarReferencias() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserName();
        NombreSocioEditText = findViewById(R.id.nSocioEditText);
        ApellidosSocioEditText = findViewById(R.id.aSocioEditText);
        CelularSocioEditText = findViewById(R.id.cSocioEditText);
        DireccionSocioEditText = findViewById(R.id.eSocioEditText);
        txtuser = findViewById(R.id.txtuserSocio);
        FNSocioEditText = findViewById(R.id.fn_SocioEditText);

        Bundle data = new Bundle();
        data = getIntent().getExtras();
        id = data.getString("id_socios");
        NombreSocio = data.getString("nombres_socios");
                NombreSocioEditText.setText(NombreSocio);
        ApellidosSocio = data.getString("apellidos_socios");
                ApellidosSocioEditText.setText(ApellidosSocio);
        CelularSocio = data.getString("celular_socios");
                CelularSocioEditText.setText(CelularSocio);
        DireccionSocio= data.getString("email_socios");
                DireccionSocioEditText.setText(DireccionSocio);
        FNSocio = data.getString("fnsocio_socios");
                FNSocioEditText.setText(FNSocio);

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
                    txtuser.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void actualizarSocio() {
        nsocio = NombreSocioEditText.getText().toString();
        asocio = ApellidosSocioEditText.getText().toString();
        csocio = CelularSocioEditText.getText().toString();
        esocio = DireccionSocioEditText .getText().toString();
        fsocio = FNSocioEditText.getText().toString();
        if (nsocio.equals("") || asocio.equals("") || csocio.equals("") || esocio.equals("") || fsocio.equals("")){
            mostrarErrores();
        }else{
            HashMap map = new HashMap();
            map.put("nombreSocio",nsocio);
            map.put("apellidosSocio",asocio);
            map.put("celularSocio",csocio);
            map.put("direccionSocio",esocio);
            map.put("fnsocio",fsocio);
            databaseReference.child("Socio").child(id).updateChildren(map);
            Toast.makeText(this,"Se actualizo correctamente",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void mostrarErrores() {

        if(NombreSocio.contentEquals("")){
            NombreSocioEditText.setError("Requerido");
        }
        if(ApellidosSocio.equals("")){
            ApellidosSocioEditText.setError("Requerido");
        }
        if(CelularSocio.equals("")){
            CelularSocioEditText.setError("Requerido");
        }
        if(DireccionSocio.equals("")){
            DireccionSocioEditText.setError("Requerido");
        }
        if(FNSocio.equals("")){
            FNSocioEditText.setError("Requerido");
        }
    }

}