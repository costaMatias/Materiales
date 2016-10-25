package costasoft.materialapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benjamin Costa on 20/10/2016.
 */
public class MaterialesAdapter extends RecyclerView.Adapter<MaterialesAdapter.MaterialViewHolder> {
    private List<Material> items;

    public static class MaterialViewHolder extends RecyclerView.ViewHolder{
        public TextView marca;
        public TextView descripcion;
        public TextView precio;

        public MaterialViewHolder(View v){
            super(v);
            marca = (TextView)v.findViewById(R.id.tv_marca);
            descripcion = (TextView)v.findViewById(R.id.tv_descripcion);
            precio = (TextView)v.findViewById(R.id.tv_precio);

        }
    }

    public MaterialesAdapter(List<Material> items){
        this.items = items;
    }

    @Override
    public MaterialesAdapter.MaterialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_tarjeta, parent, false);
        return new MaterialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MaterialesAdapter.MaterialViewHolder holder, int position) {
        holder.marca.setText(items.get(position).getMarca());
        holder.descripcion.setText(items.get(position).getDescripcion());
        holder.precio.setText(String.valueOf(items.get(position).getPrecio()).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
