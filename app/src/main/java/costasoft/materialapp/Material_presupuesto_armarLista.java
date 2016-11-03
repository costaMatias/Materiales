package costasoft.materialapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;

public class Material_presupuesto_armarLista extends AppCompatActivity {
    private DB_MaterialOperaciones db;
    private RecyclerView recycler;
    private MaterialesAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Material> listaItem;
    private ArrayList<MaterialPresupuesto> listaPresupuesto = new ArrayList<MaterialPresupuesto>();
    private EditText filtroDescrip,filtroMarca;
    public MaterialPresupuesto prodAux;
    public int cant;
    private CheckBox checkMarca,checkDescrip;
    private FloatingActionButton btn_filtro,btn_send;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_presupuesto_lista);

        //App bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Materiales - Presupuesto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_container);
        collapsingToolbarLayout.setTitle("Filtraje");
        prodAux = new MaterialPresupuesto(cant);


        db = new DB_MaterialOperaciones(this);
        //Vinculación de elementos de ui
        filtroMarca = (EditText)findViewById(R.id.et_filtro);
        filtroDescrip = (EditText)findViewById(R.id.et_filtro2);
        checkDescrip = (CheckBox)findViewById(R.id.chDescrip);
        checkMarca = (CheckBox)findViewById(R.id.chMarca);
        btn_filtro = (FloatingActionButton)findViewById(R.id.filtro);
        btn_send = (FloatingActionButton)findViewById(R.id.btn_share);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            filtroDescrip.setText(bundle.getString("datoRefer"));
            checkDescrip.setChecked(true);
        }else{
            filtroDescrip.setText("");
            filtroMarca.setText("");
            checkDescrip.setChecked(true);
            checkMarca.setChecked(true);
        }
        if (checkDescrip.isChecked() && (!checkMarca.isChecked())) {
            buscarDescripcion(filtroDescrip.getText().toString());
            //Accion de filtraje
            filtroDescrip.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    buscarDescripcion(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if ((checkMarca.isChecked()) && (checkDescrip.isChecked())) {
            buscarFullFiltraje(filtroMarca.getText().toString(), filtroDescrip.getText().toString());
        }

        btn_filtro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if((checkDescrip.isChecked()) && !(checkMarca.isChecked())){
                    buscarDescripcion(filtroDescrip.getText().toString());
                    //Accion de filtraje
                    filtroDescrip.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            buscarDescripcion(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                if ((checkMarca.isChecked()) && (checkDescrip.isChecked())) {
                    buscarFullFiltraje(filtroMarca.getText().toString(), filtroDescrip.getText().toString());
                }
                else{
                    buscarFullFiltraje("","");
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Material_presupuesto_listaConfirmar.class);
                i.putParcelableArrayListExtra("lista",listaPresupuesto);
                startActivity(i);

            }
        });
    }

    public void buscarFullFiltraje(String marca,String descripcion){
        listaItem = db.buscarMaterialFullFiltro(marca,descripcion);
        inicializarRecicler(listaItem);
    }

    public void buscarDescripcion(String descripcion){
        listaItem = db.buscarMaterial(descripcion);
    }

    public AlertDialog createDialogCantidad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Material_presupuesto_armarLista.this);

        LayoutInflater inflater = Material_presupuesto_armarLista.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.alertdialog_cantidadmaterial, null);

        builder.setView(v);

        final EditText etCant = (EditText)v.findViewById(R.id.et_cantidadProds);
        TextView tvDes = (TextView)v.findViewById(R.id.tv_descrip_alertDialog);
        builder.setCancelable(true);
        tvDes.setText("Indique cantidad a solicitar");
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cant= Integer.parseInt(etCant.getText().toString());
                Log.d("cantEt",etCant.getText().toString());
                prodAux = new MaterialPresupuesto(cant);
                Log.i("AuxCantDentro",String.valueOf(prodAux.getCantidad()));
                //Log.d("cantAuxAux",String.valueOf(prodAux.getCantidad()));
            }
        });

        return builder.create();
    }


    private void inicializarRecicler(ArrayList<Material> items){
        recycler = (RecyclerView)findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new MaterialesAdapter(items,this);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("PruebaTouchProducts","Pulsó descripcion: "+adapter.obtenerItem(recycler.getChildAdapterPosition(v)).getDescripcion());

                createDialogCantidad().show();

                Log.i("AuxCantFuera",String.valueOf(prodAux.getCantidad()));
                MaterialPresupuesto prod = new MaterialPresupuesto(prodAux.getCantidad());
                prod.setDescripcion(adapter.obtenerItem((recycler.getChildAdapterPosition(v))).getDescripcion());
                prod.setMarca(adapter.obtenerItem(recycler.getChildAdapterPosition(v)).getMarca());
                prod.setPrecio(adapter.obtenerItem(recycler.getChildAdapterPosition(v)).getPrecio());
                prod.setCantidad(prodAux.getCantidad());
                Log.i("prodCant",String.valueOf(prod.getCantidad()));
                listaPresupuesto.add(prod);
                Log.i("ListaPres","Size: "+listaPresupuesto.size());
                Log.i("cant",String.valueOf(prod.getCantidad()));
            }
        });
        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());
    }






    //Boton atras acción
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                listaPresupuesto.clear();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
