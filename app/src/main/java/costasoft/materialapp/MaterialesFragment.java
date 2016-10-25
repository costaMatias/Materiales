package costasoft.materialapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import costasoft.materialapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialesFragment extends Fragment {
    private Button btn_nuevoMaterial,btn_mostrarTodos,btn_favs;

    public MaterialesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_materiales, container, false);
        btn_nuevoMaterial = (Button)view.findViewById(R.id.btn_nuevoprod);
        btn_mostrarTodos = (Button)view.findViewById(R.id.btn_mostrarProductos);


        btn_nuevoMaterial.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(getContext(), InsertActivity.class);
                startActivity(i);
            }
        });

        btn_mostrarTodos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
             Intent i = new Intent(getContext(), MaterialBuscar.class);
                startActivity(i);
            }
        });



        return view;
    }

}
