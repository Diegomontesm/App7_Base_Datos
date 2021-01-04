package com.example.a7_basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//CREAMOS LOS OBJETOS DE TIPO EDITTEXT
    private EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //CREAMOS LA RELACION ENTRE LA PARTE LOGICA Y LA PARTE GRAFICA
        et_codigo  = (EditText)findViewById(R.id.txt_codigo);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        et_precio = (EditText)findViewById(R.id.txt_precio);
    }
    //[1]METODO PARA DAR DE ALTA LOS PRODUCTOS
    public void Registrar(View view){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
    //ABRIR LA BD MODO ESCRITURA Y LECTURA
        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();
    //OBTENER LO QUE EL USUARIO INGRESA
        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();
        //VALIDACION
        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            //GUARDAR DENTRO DE LA BASE DE DATOS LOS VALORES QUE EL USUARIO ESTA INGRESANDO
            registro.put("codigo" , codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            sqLiteDatabase.insert("articulos", null, registro);
            sqLiteDatabase.close();

            //LIMPIAMOS
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            //AVISAMOS AL USUARIO EL REGISTRO EXITOSO
            Toast.makeText(this, "El registro fue exitoso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //[2]Mètodo para consultar la existencia de productos
    public void Buscar(View view){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //CREAR LA APERTURA EN MODO LECTURA Y ESCRITURA
        SQLiteDatabase database = adminSQLiteOpenHelper.getWritableDatabase();
        //OBTENER LO QUE EL USUARIO INGRESE A LA APP
        String codigo = et_codigo.getText().toString();
        if(!codigo.isEmpty()){
            Cursor fila = database.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo = " + codigo, null) ;//RAWQUERY("SENTENCIA A EJECUTAR") APLICA UN SELECT
            //RETORNA VERDAD EN CASO DE ENCONTRAR DATOS EN LA TABLA
            if(fila.moveToFirst()){//REVISA SI LA CONSULTA TIENE VALORES Y SI LOS TIENE LOS MOSTRAMOS
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                //CERRAR BD
                database.close();
            }else{
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else{
        Toast.makeText(this, "Debe introducir el código del artículo", Toast.LENGTH_LONG).show();
        }
    }
    //[3]METODO PARA DAR DE BAJA UN ARTICULO
    public void Borrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null,  1);
        SQLiteDatabase database = admin.getWritableDatabase();//ABRIR LA BD EN MODO LECTURA Y ESCIRURA
        //OBTENER LO QUE EL USUARIO INGRESE
        String codigo = et_codigo.getText().toString();
        //VALIDACION DEL CAMPO
        if(!codigo.isEmpty()){
            int cantidad = database.delete("articulos", "codigo=" + codigo, null );
            //SE DECLARA VARIABLE TIPO ENTERO PORQUE DELETE RETORNA ENTERO QUE ES LA CANT DE REGISTROS BORRADOS
            //ESTE METODO DEBE RETORNAR ENTEROS, SI BORRA UN DATO EL VALOR QUE SE RETORNA EN CANTIDAD ES 1
            database.close();//CERRAR LA DB
            //LIMPIAR LOS CAMPOS
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");
            //INDICARLE AL USUARIO QUE FUE BORRADO
            if(cantidad==1){//1 PORQUE SE RETORNA UN 1 CUANDO SE HA BORRADO, 0 SI NO EXISTE
                Toast.makeText(this, "El artículo fue eliminado exitosamente", Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(this, "ERROR: El artículo no existe", Toast.LENGTH_SHORT);
            }
        }else{
            Toast.makeText(this, "Debes introducir un valor en el campo código", Toast.LENGTH_SHORT).show();
        }
    }
    //[4] METODO PARA MODIFICAR UN ARTICULO
    public void Modificar(View view){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();
        //OBTENER LOS DATOS DE LOS USUARIOS
        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();
        //VALIDACION DE LOS DATOS APRA QUE EL USUARIO NO DEJE EN BLANCO
        if(!codigo.isEmpty() && descripcion.isEmpty() && precio.isEmpty()){
            ContentValues registro = new ContentValues();//CREAR OBJETO REGISTRO
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            //GUARDAR DENTRO DE LA BASE DE DATOS
            int cantidad = sqLiteDatabase.update("articulos", registro, "codigo = " + codigo, null);
            sqLiteDatabase.close();
            //1 si los registros se modificaron
            if(cantidad == 1){
            Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_SHORT);
            }else{
            Toast.makeText(this, "No se modificaron los artículos", Toast.LENGTH_SHORT);
            }

        }else{
            Toast.makeText(this, "Debe llenar todos los campos a modificar", Toast.LENGTH_LONG).show();
        }
    }

}