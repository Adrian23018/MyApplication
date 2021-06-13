package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {

    private EditText email, pass, nombre, id;
    private Button boton, boton2;


    private String nombre1 = "";
    private String email1 = "";
    private String password = "";
    private String id1 = "";
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = (EditText) findViewById(R.id.ediname);
        pass = (EditText) findViewById(R.id.ediPassword);

        nombre = (EditText) findViewById(R.id.edinombre);

        boton = findViewById(R.id.btnRegistrar);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre1 = nombre.getText().toString();
                email1 = email.getText().toString();
                password = pass.getText().toString();


                // FirebaseDatabase database= FirebaseDatabase.getInstance();
                // DatabaseReference myref=database.getReference("usuarios");

                if (!email1.isEmpty() && !password.isEmpty() && !nombre1.isEmpty()) {

                    if (password.length() >= 6) {
                        registroUser();
                    } else {
                        Toast.makeText(RegistrarUsuario.this, "El Password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Campos vacios ", Toast.LENGTH_LONG).show();

                }
            }

        });

    }

    private void registroUser() {
        mAuth.createUserWithEmailAndPassword(email1, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", nombre1);
                    map.put("email", email1);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(RegistrarUsuario.this, "USUARIO REGISTARDO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrarUsuario.this, HomeFragment.class));
                                finish();
                            } else
                                Toast.makeText(RegistrarUsuario.this, "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(RegistrarUsuario.this, "No se pudo registar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

  /*  public void log(View view)
    {
        Intent siguiente=new Intent(this, HomeFragment.class);
        startActivity(siguiente);
    }

}*/