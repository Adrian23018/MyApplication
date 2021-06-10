package com.example.myapplication.ui.slideshow;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    Spinner combo;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    EditText horaI,horaF;
    Button guardar;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Date d=new Date();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        horaI=root.findViewById(R.id.ediHoraIni);
        SimpleDateFormat ho=new SimpleDateFormat("h:mm a");
        String horaString = ho.format(d);
        horaI.setText(horaString);

        horaF=root.findViewById(R.id.ediHoraFin);
        SimpleDateFormat ho2=new SimpleDateFormat("h:mm a");
        String horaString2 = ho2.format(d);
        horaF.setText(horaString2);

        guardar=root.findViewById(R.id.btnGuardar2);


       combo=root.findViewById(R.id.spinner);
       cargarDatos();


       guardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               GuardarDatos();
           }
       });

        return root;
    }

    public void GuardarDatos() {
        String inicio = horaI.getText().toString();
        String finals = horaF.getText().toString();
        String spinner = combo.getSelectedItem().toString();

        ConexionTiempo conexion = new ConexionTiempo(getActivity().getApplicationContext(), "TIEMPO", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("horaInicial", inicio);
            registroNuevo.put("horaFinal", finals);
            registroNuevo.put("actividad", spinner);

            db.insert("tiempo", null, registroNuevo);
            Toast.makeText(getActivity().getApplicationContext(), "Datos Almacenados", Toast.LENGTH_SHORT).show();
        }
    }



    public void cargarDatos(){
        Conexion conexion=new Conexion(getActivity().getApplicationContext(),"ACTIVIDAD2",null,1);
        lista=conexion.llenar();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        combo.setAdapter(adaptador);
    }
}