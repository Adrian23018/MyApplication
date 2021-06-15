package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.conexion.Conexion;

import java.util.ArrayList;

public class Logeado extends AppCompatActivity {


    ArrayList<String> lista;
    ArrayAdapter adaptador;


    SQLiteDatabase db;
    RadioGroup grupoRadio;
    RadioButton productivo, Noproductivo;
    EditText nombre1;
    TextView txtRadioSeleccionado, resultados2, usuario,nombreUsu;
    ListView res;
    Button guardar, mostrar;
    private static final String ARG_PARAM1="usuario";

    private String usuario1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logeado);

        nombre1 = findViewById(R.id.ediNombre);
        grupoRadio = findViewById(R.id.grupoRadio);
        productivo = findViewById(R.id.radioProductiva);
        Noproductivo = findViewById(R.id.radioNoProductiva);
        txtRadioSeleccionado = findViewById(R.id.resultado);
        guardar = findViewById(R.id.btnEGuardar);
        resultados2 = findViewById(R.id.respuesta);
        mostrar = findViewById(R.id.btnMostar);
        res = findViewById(R.id.lista);
        usuario=findViewById(R.id.nombreUsu);


        Bundle datosRecibidos = getIntent().getExtras(); // -----> aqui se recibe los datos empaquetados en el Bundle denominados datos en la clase MainActivity
        usuario.setText("  "+datosRecibidos.getString("email1"));


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

    }

    public void GuardarDatos() {
        String nombres = nombre1.getText().toString();
        String selecionar = txtRadioSeleccionado.getText().toString();
        String email  = usuario.getText().toString();

        Conexion conexion = new Conexion(this, "USUACTIVIDAD", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        if (db != null) {
            System.out.println("Entro");
            ContentValues registroNuevo = new ContentValues();
            registroNuevo.put("email", email);
            registroNuevo.put("nombre", nombres);
            registroNuevo.put("selecionar", selecionar);

            db.insert("registro", null, registroNuevo);
            Toast.makeText(this, "Datos Almacenados", Toast.LENGTH_SHORT).show();
        }
    }



    public void consultar(){
        Conexion conexion=new Conexion(this,"USUACTIVIDAD",null,1);
        lista=conexion.llenar_lv();
        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        res.setAdapter(adaptador);

    }
}