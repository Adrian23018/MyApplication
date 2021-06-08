package com.example.myapplication.ui.gallery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.conexion.Conexion;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    ArrayList<String> lista;
    ArrayAdapter adaptador;


    SQLiteDatabase db;
    RadioGroup grupoRadio;
    RadioButton productivo, Noproductivo;
    EditText nombre1;
    TextView txtRadioSeleccionado, resultados2;
    ListView res;
    Button guardar, mostrar;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View r = inflater.inflate(R.layout.fragment_gallery, container, false);
       // final TextView textView = root.findViewById(R.id.text_gallery);

        nombre1 = r.findViewById(R.id.ediNombre);
        grupoRadio = r.findViewById(R.id.grupoRadio);
        productivo = r.findViewById(R.id.radioProductiva);
        Noproductivo = r.findViewById(R.id.radioNoProductiva);
        txtRadioSeleccionado = r.findViewById(R.id.resultado);
        guardar = r.findViewById(R.id.btnEGuardar);
        resultados2 = r.findViewById(R.id.respuesta);
        mostrar = r.findViewById(R.id.btnMostar);
        res = r.findViewById(R.id.lista);


        grupoRadio.clearCheck();

        grupoRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (productivo.isChecked()) {
                    txtRadioSeleccionado.setTextColor(0xff00ff00);
                    txtRadioSeleccionado.setText(productivo.getText());
                } else if (Noproductivo.isChecked()) {
                    txtRadioSeleccionado.setTextColor(0xff00ff00);
                    txtRadioSeleccionado.setText(Noproductivo.getText());
                }

            }
        });



        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cargar();
                consultar();
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validarCampos();
                GuardarDatos();
            }
        });


        return r;


    }

    public void GuardarDatos() {
        String nombres = nombre1.getText().toString();
        String selecionar = txtRadioSeleccionado.getText().toString();

        Conexion conexion = new Conexion(getActivity().getApplicationContext(), "ACTIVIDAD2", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("nombre", nombres);
            registroNuevo.put("selecionar", selecionar);

            db.insert("agenda", null, registroNuevo);
            Toast.makeText(getActivity().getApplicationContext(), "Datos Almacenados", Toast.LENGTH_SHORT).show();
        }
    }



    public void consultar(){
        Conexion conexion=new Conexion(getActivity().getApplicationContext(),"ACTIVIDAD2",null,1);
        lista=conexion.llenar_lv();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        res.setAdapter(adaptador);

    }




}