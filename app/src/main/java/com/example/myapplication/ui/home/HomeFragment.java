package com.example.myapplication.ui.home;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Logeado;
import com.example.myapplication.Login;
import com.example.myapplication.Main2Activity;
import com.example.myapplication.R;
import com.example.myapplication.RegistrarUsuario;
import com.example.myapplication.conexion.ConexionUsuario;
import com.example.myapplication.ui.gallery.GalleryFragment;
import com.example.myapplication.ui.registro.RegistroU;
import com.example.myapplication.ui.registro.RegistroUsuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

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
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View r = inflater.inflate(R.layout.fragment_home, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(null);

        email = r.findViewById(R.id.ediEmail);
        pass = r.findViewById(R.id.ediPassword);
        boton= r.findViewById(R.id.btnIniciar);
        usu=r.findViewById(R.id.usuario);

       

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1= email.getText().toString();
                password=pass.getText().toString();

                if(!email1.isEmpty() && !password.isEmpty()){
                    loginUser2();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"CAMPOS VACIOS ", Toast.LENGTH_SHORT).show();

                }
            }
        });



        return r;
    }

    private void loginUser2(){

        ConexionUsuario conexion = new ConexionUsuario(getActivity(), "Usuarios", null, 1);
        String u=email.getText().toString();
        String p=pass.getText().toString();

        if(u.equals("")&& p.equals("")){
            Toast.makeText(getActivity(), "CAMPO VACIOS", Toast.LENGTH_SHORT).show();
        }else if(conexion.loginA(u,p)==1)
        {
            Toast.makeText(getActivity(), "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
            usu.setText(u);
        }
        else {
            Toast.makeText(getActivity(), "DATOS INCORRECTOS", Toast.LENGTH_SHORT).show();
        }
    }

}

