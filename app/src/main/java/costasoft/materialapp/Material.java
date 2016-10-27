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
        String pre =  (jsonObject.getString("Precio final"));
        boolean coma = false;
        for(int i = 0;i<pre.length();i++){
         if(pre.charAt(i)==','){
             pre = pre.replace(pre.charAt(i),'.');
             coma = true;
         }
        }
        if(coma==false){
            if((Float.valueOf(pre)>0))
            {
                pre.concat(",0");
            }
            else {
                pre = "0.0";
            }

        }
        this.precio = Float.valueOf(pre);
        this.descripcion = jsonObject.getString("Descripción");
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
