package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    EditText usuarioEditText,passwordUsuarioEditText;
    Button btnRegistrarUsuario;
    String nombreUsuario,passwordUsuario;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        usuarioEditText = findViewById(R.id.usuarioEditText);
        passwordUsuarioEditText = findViewById(R.id.passwordUsuarioEditText);
        btnRegistrarUsuario=findViewById(R.id.btnRegistrarUsuario);
        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreUsuario = usuarioEditText.getText().toString();
                passwordUsuario= passwordUsuarioEditText.getText().toString();
                if (!nombreUsuario.isEmpty() && !passwordUsuario.isEmpty())
                {
                    if (passwordUsuario.length()>=6) {
                        registrarUsuario();
                    }else {
                        Toast.makeText(RegistrarUsuarioActivity.this, "La contraseña debe tener como minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegistrarUsuarioActivity.this, "Por favor complete los campos requeridos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void registrarUsuario() {
        usuarioEditText = findViewById(R.id.usuarioEditText);
        passwordUsuarioEditText = findViewById(R.id.passwordUsuarioEditText);
        nombreUsuario = usuarioEditText.getText().toString();
        passwordUsuario= passwordUsuarioEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(nombreUsuario,passwordUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("email", nombreUsuario);
                    map.put("password",passwordUsuario);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful())
                            {
                                startActivity(new Intent(RegistrarUsuarioActivity.this,AuthActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistrarUsuarioActivity.this, "No se pudo registar en la BD ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{

                    Toast.makeText(RegistrarUsuarioActivity.this, "No se pudo registar el usuario ", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


}

