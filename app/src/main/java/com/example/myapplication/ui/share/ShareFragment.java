package com.example.myapplication.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;

public class ShareFragment extends Fragment {
    PieChart pieChart;

    private ShareViewModel shareViewModel;
    ArrayList<String>valoresX = new ArrayList<>();
    ArrayList<Entry>valoresY = new ArrayList<>();
    ArrayList<Integer>colores = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
       // final TextView textView = root.findViewById(R.id.text_share);
        pieChart = root.findViewById(R.id.pcGrafica);

        // DEFINIMOS ALGUNOS ATRIBUTOS
        pieChart.setHoleRadius(40f);
        pieChart.setDrawXValues(true);
        pieChart.setDrawYValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        // CREAMOS UNA LISTA PARA LOS VALORES X
        valoresX.add("Improductivo");
        //valoresX.add("Ventas");
        valoresX.add("Productivo");

        // CREAMOS UNA LISTA PARA LOS VALORES DE Y
        valoresY.add(new Entry(50, 0));  //valor Improductivo
       // valoresY.add(new Entry(10, 1));
        valoresY.add(new Entry(50, 2));  //valor Productivo

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
        pieChart.setDescription("Registro de productividad");

        // OCULTAR LEYENDA
        pieChart.setDrawLegend(true);

        return root;
    }
}