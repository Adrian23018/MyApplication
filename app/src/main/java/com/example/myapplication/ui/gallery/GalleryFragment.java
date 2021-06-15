package com.example.myapplication.ui.gallery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.conexion.Conexion;
import com.example.myapplication.conexion.ConexionUsuario;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    ArrayList<String> lista;
    ArrayAdapter adaptador;


    SQLiteDatabase db;
    RadioGroup grupoRadio;
    RadioButton productivo, Noproductivo;
    EditText nombre1;
    TextView txtRadioSeleccionado, resultados2, usuario;
    ListView res;
    Button guardar, mostrar;
    private static final String ARG_PARAM1="usuario";
    Spinner combo;

    private String usuario1;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View r = inflater.inflate(R.layout.fragment_gallery, container, false);
        View t = inflater.inflate(R.layout.fragment_home, container, false);
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
        usuario=t.findViewById(R.id.usuariot);
        combo=r.findViewById(R.id.spinner);

        cargarDatos();

        if(getArguments() !=null)
        {
         usuario1=getArguments().getString(ARG_PARAM1);
        }

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

                consultar();
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GuardarDatos();
            }
        });


        return r;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
            return;
        }
        // Y ahora puedes recuperar usando get en lugar de put

        String nombre = datosRecuperados.getString("nombre");
        // Imprimimos, pero en tu caso haz lo necesario

        Log.d("GalleryFragment", "El nombre: " + nombre);
    }

    public void GuardarDatos() {

        String nombres = nombre1.getText().toString();
        String selecionar = txtRadioSeleccionado.getText().toString();
        String email =combo.getSelectedItem().toString();


        Conexion conexion = new Conexion(getActivity(), "USUACTIVIDAD", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("email", email);
            registroNuevo.put("nombre", nombres);
            registroNuevo.put("selecionar", selecionar);

            db.insert("registro", null, registroNuevo);
            Toast.makeText(getActivity(), "Datos Almacenados BD USUACTIVIDAD", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultar(){
        Conexion conexion=new Conexion(getActivity(),"USUACTIVIDAD",null,1);
        lista=conexion.llenar_lv();
        adaptador=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);
        res.setAdapter(adaptador);

    }
    public static GalleryFragment newInstance(String usuario1){
        GalleryFragment f = new GalleryFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1,usuario1);
        f.setArguments(args);

        return f;
    }

    public void cargarDatos(){
        ConexionUsuario conexion=new ConexionUsuario(getActivity().getApplicationContext(),"Usuarios",null,1);
        lista=conexion.llenar();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        combo.setAdapter(adaptador);
    }




}