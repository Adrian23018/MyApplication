package com.example.myapplication.ui.registro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.conexion.ConexionUsuario;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class RegistroU extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText email, pass, nombre, id;
    private Button boton, boton2;


    private String nombre1 = "";
    private String email1 = "";
    private String password = "";
    private String id1 = "";
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View r = inflater.inflate(R.layout.fragment_gallery, container, false);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = (EditText) r.findViewById(R.id.ediname);
        pass = (EditText) r.findViewById(R.id.ediPassword);

        nombre = (EditText) r.findViewById(R.id.edinombre);

        boton = r.findViewById(R.id.btnRegistrar);
        boton2=r.findViewById(R.id.btnRegistrar2);


        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarDatos();
                TextView enviar=(TextView) getActivity().findViewById(R.id.nombreUsu);
                enviar.setText(nombre.getText().toString());

               /* Bundle datos= new Bundle();
                datos.putString("usuario",nombre.getText().toString());
                GalleryFragment fragment=new GalleryFragment();
                fragment.setArguments(datos);
                getSupportFragmentManager().beginTransaction().add(R.id.nombreUsu,fragment).commit();*/

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


         /*   Intent intent = new Intent(RegistrarUsuario.this, GalleryFragment.class);
            Bundle datos = new Bundle();
            datos.putString("Email", email.getText().toString());
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