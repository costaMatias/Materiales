package costasoft.materialapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MaterialBuscar extends AppCompatActivity {
    EditText DatoBuscar;
    Button btn_allProducts, btn_searchProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_buscar);

        btn_allProducts = (Button)findViewById(R.id.btn_buscarTodos);
        DatoBuscar = (EditText)findViewById(R.id.et_Buscardescripcion);
        btn_searchProduct = (Button) findViewById(R.id.btn_buscar);



        btn_searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),materialesList.class);
                i.putExtra("datoRefer",DatoBuscar.getText().toString());
                startActivity(i);
            }
        });



        btn_allProducts.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Intent i = new Intent(getBaseContext(),materialesList.class);
                startActivity(i);
            }
        });
    }
}