package costasoft.materialapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Benjamin Costa on 20/10/2016.
 */
public class Material {
    private int codigo;
    private String marca;
    private String descripcion;
    private float precio;


    //Constructores

    public Material(){
        this.codigo = 0;
        this.marca = null;
        this.descripcion = null;
    }

    public Material(String mark, String descrip,float precioFinal){
        this.codigo = 0;
        this.marca = mark;
        this.descripcion = descrip;
        this.precio = precioFinal;
    }

    public Material(JSONObject jsonObject) throws JSONException {
        this.codigo = jsonObject.getInt("codigo");
        this.marca = jsonObject.getString("Marca");
        this.descripcion = jsonObject.getString("Descripción");
        this.precio = 0;

    }

    //Métodos Set

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    //Métodos Get
    public int getCodigo() {
        return codigo;
    }

    public String getMarca() {
        return marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }
}
