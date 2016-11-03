package costasoft.materialapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Benjamin Costa on 30/10/2016.
 */
public class MaterialPresupuesto extends Material implements Parcelable{
        private int cantidad;


        //Constructores
        public MaterialPresupuesto(String mark, String descrip,float precioFinal,int cantidad){
            super(mark,descrip,precioFinal);
            this.cantidad = cantidad;
        }

    public MaterialPresupuesto() {
        super();
        this.cantidad=0;
    }
    public MaterialPresupuesto(int cantidad){
        super();
        this.cantidad=cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public MaterialPresupuesto(Parcel in){
            super();
            readFromParcel(in);

        }
        public static final Parcelable.Creator<MaterialPresupuesto> CREATOR = new Parcelable.Creator<MaterialPresupuesto>() {
            public MaterialPresupuesto createFromParcel(Parcel in) {
                return new MaterialPresupuesto(in);
            }
            public MaterialPresupuesto[] newArray(int size){
                return new MaterialPresupuesto[size];
            }
        };

    private void readFromParcel(Parcel in) {
        this.setCodigo(in.readInt());
        this.setMarca(in.readString());
        this.setDescripcion(in.readString());
        this.setPrecio(in.readFloat());
        this.setCantidad(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getCodigo());
        dest.writeString(this.getMarca());
        dest.writeString(this.getDescripcion());
        dest.writeFloat(this.getPrecio());
        dest.writeInt(this.getCantidad());
    }
}
