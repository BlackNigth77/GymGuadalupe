package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.UUID;

public class OjetivosActivity extends AppCompatActivity {
    //Crear una instancia de Firebase para las transacciones
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    EditText EspaldaEditText, caderaEditText , PesoEditText,IMCEditText, CinturaEditText ;
    Button btnRegistrarObjetivo;
    String espalda,cadera,peso,IMC,cintura,fecha,id,usuario;
    Boolean accionregistrar = true;
    TextView txtFecha,titulo_objetivo,txt_usuario_obj;
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojetivos);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtFecha = findViewById(R.id.txtFecha);
        txtFecha.setText(date);
        inicializarFirebase();
        asignarReferencias();
        getUserName();
        verificarAccion();

    }

   /* private cargarDatos(){
        Bundle data = new Bundle();
        data = getIntent().getExtras();
        id = data.getString("");
        NombreSocio = data.getString("espalda");
        NombreSocioEditText.setText(NombreSocio);
        ApellidosSocio = data.getString("apellidos_socios");
        ApellidosSocioEditText.setText(ApellidosSocio);
        CelularSocio = data.getString("celular_socios");
        CelularSocioEditText.setText(CelularSocio);
        DireccionSocio= data.getString("email_socios");
        DireccionSocioEditText.setText(DireccionSocio);
        FNSocio = data.getString("fnsocio_socios");
        FNSocioEditText.setText(FNSocio);
    }*/



    private void verificarAccion() {
        if (getIntent().hasExtra("id"))
        {
            accionregistrar = false;
            setTitle("Objetivos");
            titulo_objetivo.setText("ACTUALIZAR OBJETIVO");
            id = getIntent().getStringExtra("id");
            EspaldaEditText.setText(getIntent().getStringExtra("espalda"));
            caderaEditText.setText(getIntent().getStringExtra("cadera"));
            PesoEditText.setText(getIntent().getStringExtra("peso"));
            IMCEditText.setText(getIntent().getStringExtra("IMC"));
            CinturaEditText.setText(getIntent().getStringExtra("cintura"));
            txtFecha.setText(getIntent().getStringExtra("fecha"));
            btnRegistrarObjetivo.setText("ACTUALIZAR OBJETIVO");
        }else{
            accionregistrar = true;
            setTitle("Objetivos");
            titulo_objetivo.setText("REGISTRAR OBJETIVO");
            EspaldaEditText.setText("");
            caderaEditText.setText("");
            PesoEditText.setText("");
            IMCEditText.setText("");
            CinturaEditText.setText("");
            txtFecha.setText(date);
            btnRegistrarObjetivo.setText("REGISTRAR OBJETIVO");
        }
    }

    private void asignarReferencias() {
        titulo_objetivo = findViewById(R.id.titulo_objetivo);
        EspaldaEditText = findViewById(R.id.EspaldaEditText);
        caderaEditText = findViewById(R.id.caderaEditText);
        PesoEditText = findViewById(R.id.PesoEditText);
        IMCEditText = findViewById(R.id.IMCEditText);
        CinturaEditText = findViewById(R.id.CinturaEditText);
        txt_usuario_obj = findViewById(R.id.txt_usuario_obj);
        btnRegistrarObjetivo = findViewById(R.id.btnRegistrarObjetivo);
        btnRegistrarObjetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accionregistrar){
                registrar();
                    startActivity(new Intent(OjetivosActivity.this,ListadoObjetivosActivity.class));
                } else{
                actualizar();
                    startActivity(new Intent(OjetivosActivity.this,ListadoObjetivosActivity.class));
                }
            }
        });
    }

    private void actualizar() {
        espalda = EspaldaEditText.getText().toString();
        cadera = caderaEditText.getText().toString();
        peso = PesoEditText.getText().toString();
        IMC = IMCEditText.getText().toString();
        cintura = CinturaEditText.getText().toString();
        fecha = txtFecha.getText().toString();
        usuario = txt_usuario_obj.getText().toString();
        if (espalda.equals("") || cadera.equals("") || peso.equals("") || IMC.equals("")|| cintura.equals("")|| fecha.equals("")){
            mostrarErrores();
        }else{
            HashMap map = new HashMap();
            map.put("espalda_objetivo",espalda);
            map.put("cadera_objetivo",cadera);
            map.put("peso_objetivo",peso);
            map.put("imc_objetivo",IMC);
            map.put("cintura_objetivo",cintura);
            map.put("fecha_registro_objetivo",date);
            map.put("user_objetivo",usuario);

            databaseReference.child("Objetivos").child(id).updateChildren(map);
            Toast.makeText(this,"Se actualizo correctamente",Toast.LENGTH_SHORT).show();

        }

    }

    private void registrar() {
        espalda = EspaldaEditText.getText().toString();
        cadera = caderaEditText.getText().toString();
        peso = PesoEditText.getText().toString();
        IMC = IMCEditText.getText().toString();
        cintura = CinturaEditText.getText().toString();
        fecha = txtFecha.getText().toString();
        usuario = txt_usuario_obj.getText().toString();
        if (espalda.equals("") || cadera.equals("") || peso.equals("") || IMC.equals("")|| cintura.equals("")|| fecha.equals("")){
            mostrarErrores();
        }else{
            Objetivos o= new Objetivos();
            o.setId_objetivo(UUID.randomUUID().toString());
            o.setEspalda_objetivo(espalda);
            o.setCadera_objetivo(cadera);
            o.setPeso_objetivo(peso);
            o.setIMC_objetivo(IMC);
            o.setCintura_objetivo(cintura);
            o.setFecha_registro_objetivo(date);
            o.setUser_objetivo(usuario);

            databaseReference.child("Objetivos").child(o.getId_objetivo()).setValue(o);
            Toast.makeText(this,"Registro correcto",Toast.LENGTH_SHORT).show();

        }
    }

    private void mostrarErrores() {
        if(espalda.contentEquals("")){
            EspaldaEditText.setError("Requerido");
        }
        if(cadera.equals("")){
            caderaEditText.setError("Requerido");
        }
        if(peso.equals("")){
            PesoEditText.setError("Requerido");
        }
        if(IMC.equals("")){
            IMCEditText.setError("Requerido");
        }
        if(cintura.equals("")){
            CinturaEditText.setError("Requerido");
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
                    txt_usuario_obj.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}