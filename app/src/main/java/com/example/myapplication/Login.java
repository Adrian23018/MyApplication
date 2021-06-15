package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.conexion.Conexion;
import com.example.myapplication.conexion.ConexionUsuario;
import com.example.myapplication.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    private EditText email, pass;
    private Button boton, registro;

    private String email1="";
    private String password="";

    private FirebaseAuth mAugth;
    private DatabaseReference mDatabase;
    DrawerLayout myDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(null);


        email =findViewById(R.id.ediEmail);
        pass = findViewById(R.id.ediPassword);
        boton= findViewById(R.id.btnIniciar);
        registro=findViewById(R.id.btnRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registarUsusario();
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    loginUser();

            }
        });
    }

    private void loginUser(){

        ConexionUsuario conexion = new ConexionUsuario(this, "Usuarios", null, 1);
      String u=email.getText().toString();
        String p=pass.getText().toString();

        if(u.equals("")&& p.equals("")){
            Toast.makeText(this, "CAMPO VACIOS", Toast.LENGTH_SHORT).show();
        }else if(conexion.loginA(u,p)==1)
        {
            Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(Login.this,Logeado.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this, "DATOS INCORRECTOS", Toast.LENGTH_SHORT).show();
        }
    }




    private void registarUsusario()
    {
        startActivity(new Intent(Login.this, RegistrarUsuario.class));
    }
}