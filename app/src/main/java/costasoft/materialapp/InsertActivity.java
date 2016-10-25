package costasoft.materialapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;


/**
 * Actividad de inserción para los materiales
 */
public class InsertActivity extends AppCompatActivity{
    EditText etMarca,etDescripcion,etPrecio;
    Button btnAgregar;
    private static String TAG = InsertActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    DB_MaterialOperaciones db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        db = new DB_MaterialOperaciones(this);

        setToolbar();
        etDescripcion = (EditText)findViewById(R.id.et_descripcion);
        etMarca = (EditText)findViewById(R.id.et_marca);
        etPrecio = (EditText)findViewById(R.id.et_precio);
        btnAgregar = (Button)findViewById(R.id.btn_nuevoprod);

        //Manejo de db
        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nuevoProducto(etMarca,etDescripcion,etPrecio);
            }
        });

    }

    public void nuevoProducto(EditText marca, EditText descrip, EditText precio){
        String prod_marca = marca.getText().toString();
        String prod_descrip = descrip.getText().toString();
        String Strprecio = precio.getText().toString();
        float prod_precio = Float.parseFloat(Strprecio);

        Material mat = new Material(prod_marca,prod_descrip,prod_precio);
        db.MaterialAñadir(mat);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if(pDialog.isShowing())
            pDialog.dismiss();
    }



}