package costasoft.materialapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import costasoft.materialapp.Material;

/**
 * Created by Benjamin Costa on 20/10/2016.
 */
public class DB_MaterialOperaciones {

    private DBHelper database;
    private SQLiteDatabase db;
    private Context thisContext;


    public DB_MaterialOperaciones(Context context){
        this.thisContext = context;
        this.database = new DBHelper(context,"administracion", null, 1);
        this.db = database.getWritableDatabase();
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

    public Material VerMaterial(){
        Material mat = null;

        return mat;
    }




    // Métodos utiles -- prox hacer una clase con ellos
    private void mostrarToastLargo(String texto) {
        Toast.makeText(thisContext,texto,Toast.LENGTH_LONG).show();
    }


}
