package com.example.myapplication.ui.registro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.EnviarMensaje;
import com.example.myapplication.Main2Activity;
import com.example.myapplication.R;
import com.example.myapplication.RegistrarUsuario;
import com.example.myapplication.conexion.ConexionUsuario;
import com.example.myapplication.ui.gallery.GalleryFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuFragment extends Fragment {

    private EditText email, pass, nombre, id;
    private Button boton;

    private RegistroUsuViewModel homeViewModel;

    private String nombre1 = "";
    private String email1 = "";
    private String password = "";

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EnviarMensaje EM;
    TextView usuarioo;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(RegistroUsuViewModel.class);
        View r = inflater.inflate(R.layout.fragment_registrousu, container, false);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) r.findViewById(R.id.ediname);
        pass = (EditText) r.findViewById(R.id.ediPassword);
        nombre = (EditText) r.findViewById(R.id.edinombre);
        boton = r.findViewById(R.id.btnRegistrar);
        usuarioo=r.findViewById(R.id.usuariot);



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre1 = nombre.getText().toString();
                email1 = email.getText().toString();
                password = pass.getText().toString();

                if (!email1.isEmpty() && !password.isEmpty() && !nombre1.isEmpty()) {

                    if (password.length() >= 6) {
                        GuardarDatos();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "EL PASSWORD DEBE TENER AL MENOS 6 CARACTERES", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "CAMPOS VACIOS ", Toast.LENGTH_LONG).show();

                }
            }

        });

        return r;
    }


    public void GuardarDatos() {
        String nombre2 = nombre.getText().toString();
        String email2 =email.getText().toString();
        String password= pass.getText().toString();

        ConexionUsuario conexion = new ConexionUsuario(getActivity().getApplicationContext(), "Usuarios", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {

            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("nombre", nombre2);
            registroNuevo.put("gmail", email2);
            registroNuevo.put("password", password);

            db.insert("usuario", null, registroNuevo);
            Toast.makeText(getActivity().getApplicationContext(), "USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();


            startActivity(new Intent(getActivity().getApplicationContext(), Main2Activity.class));


        }
        nombre.setText("");
        email.setText("");
        pass.setText("");

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
                                Toast.makeText(getActivity().getApplicationContext(), "USUARIO REGISTARDO CORRECTAMENTE ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity().getApplicationContext(), HomeFragment.class));
                                //  finish();
                            } else
                                Toast.makeText(getActivity().getApplicationContext(), "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No se pudo registar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

