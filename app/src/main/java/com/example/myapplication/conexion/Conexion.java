package com.example.myapplication.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class Conexion extends SQLiteOpenHelper {


   String query ="CREATE TABLE registro(email TEXT, nombre TEXT, selecionar TEXT);";



    public Conexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
        String q="SELECT * FROM registro";
        Cursor resgistros=database.rawQuery(q,null);

        if(resgistros.moveToFirst()){
            do {
                lista.add(" "+resgistros.getString(0)+" "+resgistros.getString(1)+" "+resgistros.getString(2));
            }while(resgistros.moveToNext());
        }
        return lista;
    }

    public ArrayList llenar(String nombre)
    {
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM registro ";
        Cursor resgistros=database.rawQuery(q,null);

        if(resgistros.moveToFirst()){
            do {
                lista.add(" "+resgistros.getString(1));
            }while(resgistros.moveToNext());
        }
        return lista;
    }

    private void consulta(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        String q = "select * from registro where email = " + email;
        Cursor cursor=db.rawQuery(q,null);


        while(cursor.moveToNext()) {

            String tp = String.valueOf(cursor.getString(cursor.getColumnIndex("email")));
            //sptipper.setSelection(tp);
        }
    }


    public String[] buscar_reg10(String buscar){

       String[] datos=new String[2];
       SQLiteDatabase database=this.getWritableDatabase();
       String q="SELECT * FROM registro WHERE nombre ='"+buscar+"'";
       Cursor registros=database.rawQuery(q,null);
       if(registros.moveToFirst()){
           for (int i = 0; i <1 ; i++) {
               datos[i]=registros.getString(i);
           }
           datos[1]="Encontrado";
       }else{
           datos[1]="No Encontrados "+buscar;
       }
        return datos;
    }


    public ArrayList buscar_reg(String buscar){

        ArrayList<String> lista=new ArrayList<>();
       //String[] datos=new String[2];
        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM registro WHERE email ='"+buscar+"'";
        Cursor registros=database.rawQuery(q,null);
        if(registros.moveToFirst()){
         do {
             lista.add(" "+registros.getString(1));
         }while(registros.moveToNext());

            System.out.println("Encontrado");
        }else{
           // datos[1]="No Encontrados "+buscar;
            System.out.println("No se encontraron");
        }
        return lista;
    }

    public ArrayList buscar_reg2(String buscar){

        ArrayList<String> lista=new ArrayList<>();

        SQLiteDatabase database=this.getWritableDatabase();
        String q="SELECT * FROM registro WHERE nombre ='"+buscar+"'";
        Cursor registros=database.rawQuery(q,null);
        if(registros.moveToFirst()){
            do {
                lista.add(" "+registros.getString(2));
            }while(registros.moveToNext());

            System.out.println("Encontrado");
        }else{
            // datos[1]="No Encontrados "+buscar;
            System.out.println("No se encontraron");
        }
        return lista;
    }



}
