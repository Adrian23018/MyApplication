package com.example.myapplication.ui.Eliminar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.conexion.ConexionUsuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EliminarFragment extends Fragment {

    private EliminarViewModel homeViewModel;

    private EditText email, pass;
    private Button boton, registro;

    private String email1="";
    private String password="";
    private TextView usu;

    private FirebaseAuth mAugth;
    private DatabaseReference mDatabase;
    DrawerLayout myDrawerLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(EliminarViewModel.class);
        View r = inflater.inflate(R.layout.fragment_eliminar, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(null);

        email = r.findViewById(R.id.ediEmail);
        pass = r.findViewById(R.id.ediPassword);
        boton= r.findViewById(R.id.btnEliminar);
        usu=r.findViewById(R.id.usuario);

       

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    loginUser2();
                Toast.makeText(getActivity(), "USUARIOS ELIMINADOS", Toast.LENGTH_SHORT).show();

            }
        });



        return r;
    }

    private void loginUser2(){

        ConexionUsuario conexion = new ConexionUsuario(getActivity(), "Usuarios", null, 1);


        conexion.loginEliminar();
    }

}

