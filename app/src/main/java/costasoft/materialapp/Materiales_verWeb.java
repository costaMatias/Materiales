package costasoft.materialapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;
import cz.msebera.android.httpclient.Header;

public class Materiales_verWeb extends AppCompatActivity {
   //ListView listado;
    DB_MaterialOperaciones db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB_MaterialOperaciones(this);
        //listado = (ListView)findViewById(R.id.listView);
        obtenerDatos();
    }


    public void obtenerDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://materiales.hol.es/obtener_material.php";

        RequestParams parametros = new RequestParams();


        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    db.MaterialAñadirArray(obtDatosJson(new String(responseBody)));

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    /*public void CargaLista(ArrayList<Material> datos){
        ArrayAdapter<Material> adapter = new ArrayAdapter<Material>(this,android.R.layout.simple_list_item_2,datos);
        listado.setAdapter(adapter);
    }*/


    public ArrayList<Material> obtDatosJson(String response){
        ArrayList<Material> listaItem = new ArrayList<Material>();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.optJSONArray("Materiales");
            String texto;

                if(object.getInt("estado")==1){
                    for(int i=0; i<jsonArray.length();i++){
                        listaItem.add(new Material(jsonArray.getJSONObject(i)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listaItem;
    }


}
