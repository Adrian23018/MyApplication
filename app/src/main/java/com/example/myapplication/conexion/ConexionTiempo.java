package com.example.myapplication.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ConexionTiempo extends SQLiteOpenHelper {


   String query ="CREATE TABLE tiempo(hora INTEGER, usuario TEXT, actividad TEXT);";



    public ConexionTiempo(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {


        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("--------->>>>"+query);
      db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        System.out.println("-->>> Hola bd");
        //db.execSQL("DROP TABLE IF EXISTS agenda");
        //onCreate(db);
    }

    public ArrayList llenar_lv()
    {
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM agenda";
        Cursor resgistros=database.rawQuery(q,null);

        if(resgistros.moveToFirst()){
            do {
                lista.add(" "+resgistros.getString(0)+" "+resgistros.getString(1));
            }while(resgistros.moveToNext());
        }
        return lista;
    }

    public ArrayList llenar()
    {
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM agenda";
        Cursor resgistros=database.rawQuery(q,null);

        if(resgistros.moveToFirst()){
            do {
                lista.add(" "+resgistros.getString(0));
            }while(resgistros.moveToNext());
        }
        return lista;
    }
//DEVOLVERME

    public String[] buscar_reg1(String buscar){

        String[] datos=new String[3];
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM tiempo WHERE usuario ='"+buscar+"'";
        Cursor registros=database.rawQuery(q,null);
        if(registros.moveToFirst()){
            for (int i = 0; i <3 ; i++) {
                datos[i]=registros.getString(i);

            }
            datos[1]="Encontrado";
        }else{
            datos[1]="No Encontrados "+buscar;
        }
        return datos;
    }

    public String[] buscar_reg10(String buscar){

        String[] datos=new String[3];
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT Sum(hora) FROM tiempo WHERE usuario ='"+buscar+"'";
        Cursor registros=database.rawQuery(q,null);
        if(registros.moveToFirst()){
            for (int i = 0; i <3 ; i++) {
                datos[i]=registros.getString(0);

            }
            datos[1]="Encontrado";
        }else{
            datos[1]="No Encontrados "+buscar;
        }
        return datos;
    }
}
