package com.example.myapplication.ui.slideshow;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.RegistrarUsuario;
import com.example.myapplication.conexion.Conexion;
import com.example.myapplication.conexion.ConexionTiempo;
import com.example.myapplication.conexion.ConexionUsuario;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment  {

    private SlideshowViewModel slideshowViewModel;
    Spinner combo, combo2;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    EditText hora,horaF;
    Button guardar, consultar;
    EditText buscar;
    TextView prueba;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Date d=new Date();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        hora=root.findViewById(R.id.ediHora);
        //SimpleDateFormat ho=new SimpleDateFormat("h:mm a");
       // String horaString = ho.format(d);
        //horaI.setText(horaString);


        prueba=root.findViewById(R.id.espacio1);


        combo=root.findViewById(R.id.spinner);
        combo2=root.findViewById(R.id.spinner2);

        guardar=root.findViewById(R.id.btnGuardar);
        consultar=root.findViewById(R.id.btnGuardar2);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Conexion db=new Conexion(getActivity().getApplicationContext(),"USUACTIVIDAD",null,1);
                String buscar2=combo.getSelectedItem().toString();
                //ArrayList datos;
                lista=db.buscar_reg(buscar2);
                adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
                combo2.setAdapter(adaptador);

                Toast.makeText(getActivity().getApplicationContext(), "Siiiiiiiii", Toast.LENGTH_SHORT).show();

            }
        });


       cargarDatos2();


       guardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               GuardarDatos();
           }
       });

        return root;
    }


    public void GuardarDatos() {
        String inicio = hora.getText().toString();
        String usuario = combo.getSelectedItem().toString();
        String actividad = combo2.getSelectedItem().toString();

        ConexionTiempo conexion = new ConexionTiempo(getActivity().getApplicationContext(), "USUTIEMPO", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("hora", inicio);
            registroNuevo.put("usuario", usuario);
            registroNuevo.put("actividad", actividad);

            db.insert("tiempo", null, registroNuevo);
            Toast.makeText(getActivity().getApplicationContext(), "Datos Almacenados BD USUTIEMPO", Toast.LENGTH_SHORT).show();
        }
    }


    public void cargarDatos2(){
        ConexionUsuario conexion=new ConexionUsuario(getActivity().getApplicationContext(),"Usuarios",null,1);
        lista=conexion.llenar2();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        combo.setAdapter(adaptador);
    }




    }
