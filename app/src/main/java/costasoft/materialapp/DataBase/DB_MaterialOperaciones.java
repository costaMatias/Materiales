package costasoft.materialapp.DataBase;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import costasoft.materialapp.Material;
import cz.msebera.android.httpclient.entity.ContentLengthStrategy;

/**
 * Created by Benjamin Costa on 20/10/2016.
 */
public class DB_MaterialOperaciones {

    private DBHelper database;
    private SQLiteDatabase db;
    private Context thisContext;
    //Diseño
    ProgressDialog progress;
    int progressStatus = 0;


    public DB_MaterialOperaciones(Context context){
        this.thisContext = context;
        this.database = new DBHelper(context,"administracion", null, 1);
        this.db = database.getWritableDatabase();
        progress = new ProgressDialog(context);

    }

    public void MaterialAñadir(Material prod){
        ContentValues productoDatos = new ContentValues();
        productoDatos.put("descripcion",prod.getDescripcion());
        productoDatos.put("marca",prod.getMarca());
        productoDatos.put("precio",(prod.getPrecio()));
        db.insert("articulos",null,productoDatos);
        db.close();
        mostrarToastLargo("El articulo se cargó correctamente");
    }

    public void MaterialAñadirArray(ArrayList<Material> prods){
        int size = prods.size();
        ContentValues productoDatos = new ContentValues();
        progress.setMessage("Añadiendo productos desde la web");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setMax(size);
        progress.show();
        int mult = 100;
        for(progressStatus=0;progressStatus<size;progressStatus++){

            productoDatos.put("descripcion",prods.get(progressStatus).getDescripcion());
            productoDatos.put("marca",prods.get(progressStatus).getMarca());
            productoDatos.put("precio",prods.get(progressStatus).getPrecio());
            db.insert("articulos",null,productoDatos);
                progress.setProgress(progressStatus);
            }
    mostrarToastLargo(size+" Artículos cargados correctamente!");
    db.close();
    }

    public void MaterialEliminarTodos(){
        progress.setMessage("Eliminando productos almacenados en base de datos local");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();
        db.execSQL("DELETE FROM articulos");
        Cursor cursor=db.rawQuery("SELECT * FROM articulos",null);
        if(cursor!=null){
            mostrarToastLargo("Eliminación errónea");
        }
        else{
            mostrarToastLargo("Eliminación exitosa!");
        }
        cursor.close();
    }

    public void MaterialMostrarTodos(){
        //hcer
    }

    public ArrayList<Material> buscarMaterial(String referencia){
        ArrayList<Material> resultados = new ArrayList<Material>();
        Cursor cursor = db.rawQuery("SELECT * FROM articulos WHERE descripcion LIKE '%"+referencia+"%'",null);
        if(cursor.moveToFirst()){
            do{
                Material prod = new Material(cursor.getString(1),cursor.getString(2),cursor.getFloat(3));
                resultados.add(prod);
            }while(cursor.moveToNext());
        }
        if(cursor !=null && !cursor.isClosed()){
            cursor.close();
        }
        return resultados;
    }

    public ArrayList<Material> buscarMaterialFullFiltro(String refMarca, String refDescrip){
        ArrayList<Material>resultados = new ArrayList<Material>();
        Cursor cursor = db.rawQuery("SELECT * FROM articulos WHERE descripcion LIKE '%"+refDescrip+"%' AND marca LIKE '%"+refMarca+"%'",null);
        if(cursor.moveToFirst()){
            do{
                Material prod = new Material(cursor.getString(1),cursor.getString(2),cursor.getFloat(3));
                resultados.add(prod);
            }while(cursor.moveToNext());
        }
        if(cursor !=null && !cursor.isClosed()){
            cursor.close();
        }
        return resultados;
    }



    public Material VerMaterial(){
        Material mat = null;

        return mat;
    }




    // Métodos utiles -- prox hacer una clase con ellos
    private void mostrarToastLargo(String texto) {
        Toast.makeText(thisContext,texto,Toast.LENGTH_LONG).show();
    }


}
