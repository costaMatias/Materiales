package costasoft.materialapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Material_detalle extends AppCompatActivity {
    private TextView tvMarca,tvPrecio,tvDescripcion;
    private FloatingActionButton buttonShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detalle);

        tvMarca = (TextView)findViewById(R.id.tv_marca);
        tvDescripcion = (TextView)findViewById(R.id.tv_descripcion);
        tvPrecio = (TextView)findViewById(R.id.tv_precio);
        buttonShare = (FloatingActionButton)findViewById(R.id.shareMaterial);

        Bundle bundle = getIntent().getExtras();
        tvMarca.setText(bundle.getString("marca"));
        tvDescripcion.setText(bundle.getString("descripcion"));
        tvPrecio.setText(String.valueOf(bundle.getFloat("precio")));
        final String whatsappEnviar[] = {tvDescripcion.getText().toString(),tvMarca.getText().toString(),tvPrecio.getText().toString()};
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWhatsApp(v,whatsappEnviar);
            }
        });

    }

    public void onClickWhatsApp(View view,String message[]) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Producto:" +"\n"+
                    "Descripcion: "+message[0]+"\n"+
                    "Marca: "+message[1]+"\n"+
                    "Precio: "+message[2];

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
