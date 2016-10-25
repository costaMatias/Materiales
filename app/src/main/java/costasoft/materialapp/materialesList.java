package costasoft.materialapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;

public class materialesList extends AppCompatActivity {
    private DB_MaterialOperaciones db;
    private RecyclerView recycler;
    private MaterialesAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Material> listaItem;
    private EditText filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiales_list);
        db = new DB_MaterialOperaciones(this);
        filtro = (EditText)findViewById(R.id.et_filtro);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            filtro.setText(bundle.getString("datoRefer"));
        }else{
            filtro.setText("");
        }
        inicializarRecicler(filtro.getText().toString());
        //Accion de filtraje
        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inicializarRecicler(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void inicializarRecicler(String refer) {

        listaItem = db.buscarMaterial(refer);
        recycler = (RecyclerView)findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new MaterialesAdapter(listaItem);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }
}
