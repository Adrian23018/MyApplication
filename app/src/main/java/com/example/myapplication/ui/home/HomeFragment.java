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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
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
    private Button boton;

    private String email1="";
    private String password="";

    private FirebaseAuth mAugth;
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View r = inflater.inflate(R.layout.fragment_home, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(null);

        email = r.findViewById(R.id.ediEmail);
        pass = r.findViewById(R.id.ediPassword);
        boton= r.findViewById(R.id.btnIniciar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1= email.getText().toString();
                password=pass.getText().toString();

                if(!email1.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"CAMPOS VACIOS ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return r;
    }


    private void loginUser(){

       // FirebaseApp.initializeApp();
        mAugth= FirebaseAuth.getInstance();
        mAugth.signInWithEmailAndPassword(email1,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //startActivity(new Intent(Logearse.this, Home.class));
                    //finish();
                    Toast.makeText(getActivity().getApplicationContext(), "SE HA INICIADO SESIÓN CORRECTAMENTE ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "NO SE HA INICIADO SESIÓN COMPRUEBE LOS DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

