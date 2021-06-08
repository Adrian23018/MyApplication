package com.example.myapplication.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.conexion.Conexion;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    Spinner combo;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

       combo=root.findViewById(R.id.spinner);
       cargarDatos();

        return root;
    }

    public void cargarDatos(){
        Conexion conexion=new Conexion(getActivity().getApplicationContext(),"ACTIVIDAD2",null,1);
        lista=conexion.llenar();
        adaptador=new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, lista);
        combo.setAdapter(adaptador);
    }
}