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
    private Button boton, boton2;

    private RegistroUsuViewModel homeViewModel;

    private String nombre1 = "";
    private String email1 = "";
    private String password = "";
    private String id1 = "";
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EnviarMensaje EM;

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
        boton2=r.findViewById(R.id.btnRegistrar2);

        nombre1=nombre.getText().toString();




        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // GuardarDatos();
/*

                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
// Aquí pon todos los datos que quieras en formato clave, valor
                //datosAEnviar.putLong("nombre",);
// Y puedes pasarle más datos..

                datosAEnviar.putString("nombre", nombre1);
// Preparar el fragmento
                Fragment fragmento = new GalleryFragment();
// ¡Importante! darle argumentos
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.usuario, fragmento);
                fragmentTransaction.addToBackStack(null);

// Terminar transición y nos vemos en el fragmento de destino
                fragmentTransaction.commit();

                //getSupportFragmentManager().beginTransaction().add(R.id.nombreUsu,fragment).commit();

*/
            }
        });

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
                        Toast.makeText(getActivity().getApplicationContext(), "El Password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Campos vacios ", Toast.LENGTH_LONG).show();

                }
            }

        });

        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EM= (EnviarMensaje) activity;
    }

    public void GuardarDatos() {
        String nombre2 = nombre.getText().toString();
        String email2 =email.getText().toString();
        String password= pass.getText().toString();


        ConexionUsuario conexion = new ConexionUsuario(getActivity().getApplicationContext(), "Usuarios", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("nombre", nombre2);
            registroNuevo.put("gmail", email2);
            registroNuevo.put("password", password);

            db.insert("usuario", null, registroNuevo);
            Toast.makeText(getActivity().getApplicationContext(), "Datos Almacenados", Toast.LENGTH_SHORT).show();
/*
           Intent intent = new Intent(getActivity().getApplicationContext(), GalleryFragment.class);
            Bundle datos = new Bundle();
            datos.putString("usuario", nombre.getText().toString());
            intent.putExtras(datos);
            startActivity(intent);*/

        }
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

