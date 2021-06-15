package com.example.myapplication.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.myapplication.conexion.ConexionTiempo;
import com.example.myapplication.conexion.ConexionUsuario;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;

public class ShareFragment extends Fragment {
    PieChart pieChart;
    Spinner combo;
    Button consultar;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    TextView datosC;
    String[] datos;


    private ShareViewModel shareViewModel;
    ArrayList<String>valoresX = new ArrayList<>();
    ArrayList<Entry>valoresY = new ArrayList<>();
    ArrayList<Integer>colores = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
       // final TextView textView = root.findViewById(R.id.text_share);
        pieChart = root.findViewById(R.id.pcGrafica);
        combo=root.findViewById(R.id.spinner);

        consultar=root.findViewById(R.id.btnFiltrar);
        datosC=root.findViewById(R.id.datosCompletos);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConexionTiempo db=new ConexionTiempo(getActivity().getApplicationContext(),"USUTIEMPO",null,1);
                //String buscar2=combo.getSelectedItem().toString();


                //ArrayList datos;
                //lista=db.buscar_reg(buscar2);

                //adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
                //combo2.setAdapter(adaptador);

                String buscar2=combo.getSelectedItem().toString();

                datos=db.buscar_reg1(buscar2);
                datosC.setText(datos[0]);
                //horas.setText(datos[1]);

                Toast.makeText(getActivity().getApplicationContext(), "Siiiiiiiii", Toast.LENGTH_SHORT).show();

            }
        });


        cargarDatos2();

        // DEFINIMOS ALGUNOS ATRIBUTOS
        pieChart.setHoleRadius(40f);
        pieChart.setDrawXValues(true);
        pieChart.setDrawYValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        // CREAMOS UNA LISTA PARA LOS VALORES X
        valoresX.add("Productivo");
        valoresX.add("No Productivo");
        valoresX.add("Poco Productivo");

        // CREAMOS UNA LISTA PARA LOS VALORES DE Y
        valoresY.add(new Entry(40, 0));
        valoresY.add(new Entry(10, 1));
        valoresY.add(new Entry(50, 2));

        // CREAMOS UNA LISTA DE LOS COLORES
        colores.add(getResources().getColor(R.color.red_flat));
        colores.add(getResources().getColor(R.color.blue_flat));
        colores.add(getResources().getColor(R.color.green_flat));

        // SETEAMOS LOS VALORES DE Y y LOS COLORES
        PieDataSet set = new PieDataSet(valoresY, "Resultados");
        set.setSliceSpace(5f);
        set.setColors(colores);

        // SETEAMOS LOS VALORES DE X
        PieData data = new PieData(valoresX, set);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        // OCULTAR DESCRIPCION
        pieChart.setDescription("PRODUCTIVIDAD DEL DIA");

        // OCULTAR LEYENDA
        pieChart.setDrawLegend(true);

        return root;
    }

    public void cargarDatos2(){
        ConexionUsuario conexion=new ConexionUsuario(getActivity().getApplicationContext(),"Usuarios",null,1);
        lista=conexion.llenar2();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        combo.setAdapter(adaptador);
    }
}