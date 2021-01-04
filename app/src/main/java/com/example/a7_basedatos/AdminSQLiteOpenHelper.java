package com.example.a7_basedatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//CLASE QUE ADMINISTRARÁ LA BASE DE DATOS
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //TABLA DONDE VAMOS A GUARDAR NUESTROS PRODUCTOS
        //CREAMOS TABLA LLAMADA ARTICULOS CON TRES CAMPOS, CODIGO-DESCRIPCION-PRECIO
        db.execSQL("Create Table Articulos(codigo int primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
