package costasoft.materialapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;

public class Material_presupuesto_listaConfirmar extends AppCompatActivity {
    private DB_MaterialOperaciones db;
    private RecyclerView recycler;
    private MaterialesAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Material> listaItem;
    private FloatingActionButton btn_send,btn_email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_presupuesto_listaconfirmar);

        //App bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Materiales - Presupuesto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DB_MaterialOperaciones(this);
        //Vinculación de elementos de ui
        btn_send = (FloatingActionButton)findViewById(R.id.btn_share);
        btn_email =(FloatingActionButton)findViewById(R.id.btn_email);
        listaItem = getIntent().getParcelableArrayListExtra("lista");
        inicializarRecicler(listaItem);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWhatsApp(v,listaItem);
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGmail(v,listaItem);
            }
        });
    }

    private void onClickGmail(View v, ArrayList<Material> array) {
        Intent gmail = new Intent(Intent.ACTION_VIEW);
        gmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
        gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { "matiasfranceschi@gmail.com" });
        gmail.setData(Uri.parse("matiasfranceschi@gmail.com"));
        gmail.putExtra(Intent.EXTRA_SUBJECT, "enter something");
        gmail.setType("plain/text");
        String mensaje="Presupuesto";
        mensaje+=agregarLinea();
        float total = 0;
        int i;
        for(i = 0; i< array.size();i++) {
            mensaje+="Descripcion: ";
            mensaje+=array.get(i).getDescripcion();
            mensaje+=SaltoLinea();
            mensaje+="Marca: ";
            mensaje+=array.get(i).getMarca();
            mensaje+=SaltoLinea();
            mensaje+="Precio: ";
            mensaje+=String.valueOf(array.get(i).getPrecio());
            mensaje+=SaltoLinea();
            mensaje+=SaltoLinea();
            total += array.get(i).getPrecio();
        }
        if(i == array.size()){
            mensaje+=agregarLinea();
            mensaje+="Precio Total: ";
            mensaje+=String.valueOf(total);
            mensaje+=agregarLinea();
        }
        Log.d("mensaje",mensaje);
        gmail.putExtra(Intent.EXTRA_TEXT, mensaje);
        startActivity(gmail);
    }


    private void inicializarRecicler(ArrayList<Material> items) {
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new MaterialesAdapter(items, this);
        recycler.setAdapter(adapter);
        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int oldPosition = recycler.getChildAdapterPosition(v);
                Material prodEliminado = adapter.obtenerItem(oldPosition);
                listaItem.remove(adapter.obtenerItem(oldPosition));
                Toast.makeText(Material_presupuesto_listaConfirmar.this, prodEliminado.getDescripcion() + " fue eliminado.", Toast.LENGTH_LONG).show();
                adapter.notifyItemRemoved(oldPosition);
                return true;
            }
        });
        recycler.setItemAnimator(new DefaultItemAnimator());
    }


    public void onClickWhatsApp(View view,ArrayList<Material> array) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String mensaje="Presupuesto";
            mensaje+=agregarLinea();
            float total = 0;
            int i;
            for(i = 0; i< array.size();i++) {
                mensaje+="Descripcion: ";
                mensaje+=array.get(i).getDescripcion();
                mensaje+=SaltoLinea();
                mensaje+="Marca: ";
                mensaje+=array.get(i).getMarca();
                mensaje+=SaltoLinea();
                mensaje+="Precio: ";
                mensaje+=String.valueOf(array.get(i).getPrecio());
                mensaje+=SaltoLinea();
                mensaje+=SaltoLinea();
                total += array.get(i).getPrecio();
            }
            if(i == array.size()){
                mensaje+=agregarLinea();
                mensaje+="Precio Total: ";
                mensaje+=String.valueOf(total);
                mensaje+=agregarLinea();
            }

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public String SaltoLinea(){
        return"\n";
    }
    public String agregarLinea(){
        return "\n--------------------------\n";
    }




    //Boton atras acción
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
