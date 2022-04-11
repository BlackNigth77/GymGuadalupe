package com.example.gymguadalupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AuthActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button btnNuevo_usuario,loginButton;
    String emailLogin,passwordLogin,loginadm;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        btnNuevo_usuario = findViewById(R.id.btnNuevo_usuario);
        btnNuevo_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this,RegistrarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailLogin = emailEditText.getText().toString();
                passwordLogin = passwordEditText.getText().toString();

                if (!emailLogin.isEmpty() || !passwordLogin.isEmpty())
                {
                    loginUser();
                }else {
                    Toast.makeText(AuthActivity.this, "Complete los datos requeridos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




    private void loginUser() {
        mAuth.signInWithEmailAndPassword(emailLogin,passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    if (emailLogin.equals("estefani@gmail.com")){
                         startActivity(new Intent(AuthActivity.this,HomeAdminActivity.class));
                         finish();
                    } else {
                        startActivity(new Intent(AuthActivity.this, HomeActivity.class));
                        finish();
                    }
                }else{
                    Toast.makeText(AuthActivity.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}

