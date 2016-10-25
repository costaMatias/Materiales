package costasoft.materialapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import costasoft.materialapp.DataBase.DB_MaterialOperaciones;

public class Materiales_eliminarTodos extends AppCompatActivity {
    DB_MaterialOperaciones db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiales_eliminar_todos);
        db = new DB_MaterialOperaciones(this);
        db.MaterialEliminarTodos();
    }
}
